package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Peer;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.BlockCandidateViewModel;
import com.topchain.node.service.MiningService;
import com.topchain.node.service.NodeService;
import com.topchain.node.util.NodeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.topchain.node.util.NodeUtils.*;

@Service
public class MiningServiceImpl implements MiningService {
    private ModelMapper modelMapper;
    private Node node;
    private NodeService nodeService;

    @Autowired
    public MiningServiceImpl(ModelMapper modelMapper, Node node, NodeService nodeService) {
        this.modelMapper = modelMapper;
        this.node = node;
        this.nodeService = nodeService;
    }

    private Transaction calculateRewardTransaction(String minerAddress) {
        Transaction rewardTransaction = new Transaction();
        long totalValue = micCoinsFromCoins(BLOCK_REWARD_COINS);
        for (Transaction transaction : this.node.getPendingTransactions()) {
            totalValue += transaction.getFee();
        }

        rewardTransaction.setFromAddress(NIL_ADDRESS);
        rewardTransaction.setToAddress(minerAddress);
        rewardTransaction.setValue(totalValue);
        rewardTransaction.setDateCreated(new Date());
        rewardTransaction.setTransferSuccessful(true);
        rewardTransaction.setMinedInBlockIndex(this.node.getBlocks().get(this.node.getBlocks().size() - 1).getIndex() + 1);
        rewardTransaction.setTransactionHash(
                hashText(serializeJSON(rewardTransaction, false)));

        return rewardTransaction;
    }

    @Override
    public BlockCandidateViewModel getBlockCandidate(String minerAddress) {
        List<Transaction> newTransactions = new ArrayList<>();
        newTransactions.add(calculateRewardTransaction(minerAddress));
        newTransactions.addAll(this.node.getPendingTransactions());

        Block blockCandidate = new Block();
        blockCandidate.setIndex(this.node.getBlocks().get(this.node.getBlocks().size() - 1).getIndex() + 1);
        blockCandidate.setTransactions(newTransactions);
        blockCandidate.setDifficulty(this.node.getDifficulty());
        blockCandidate.setBlockDataHash(hashText(serializeJSON(blockCandidate, false)));
        blockCandidate.setMinedBy(minerAddress);
        blockCandidate.setPreviousBlockHash(
                this.node.getBlocks().get(this.node.getBlocks().size() - 1).getBlockHash());

        this.node.addMiningJob(blockCandidate.getBlockDataHash(), blockCandidate);

        BlockCandidateViewModel blockCandidateViewModel = new BlockCandidateViewModel();
        blockCandidateViewModel.setIndex(blockCandidate.getIndex());
        blockCandidateViewModel.setDifficulty(blockCandidate.getDifficulty());
        blockCandidateViewModel.setTransactionsIncluded(blockCandidate.getTransactions().size());
        blockCandidateViewModel.setRewardAddress(blockCandidate.getMinedBy());
        blockCandidateViewModel.setBlockDataHash(blockCandidate.getBlockDataHash());
        blockCandidateViewModel.setExpectedReward(
                blockCandidate.getTransactions().get(0).getValue());
        return blockCandidateViewModel;
    }

    @Override
    public MinedBlockStatusViewModel submitBlock(MinedBlockModel minedBlockModel) {
        MinedBlockStatusViewModel minedBlockStatusViewModel = new MinedBlockStatusViewModel();


        Block blockCandidate = this.node.getMiningJobs().get(minedBlockModel.getBlockDataHash());
        if (blockCandidate == null) {
            minedBlockStatusViewModel.setStatus("not-found");
            minedBlockStatusViewModel.setMessage("The block is rejected.");

            return minedBlockStatusViewModel;
        }

        String blockHash = hashText(minedBlockModel.getBlockDataHash() +
                minedBlockModel.getDateCreated()+
                minedBlockModel.getNonce());
        if (!blockHash.startsWith(newString("0", blockCandidate.getDifficulty()))) {
            minedBlockStatusViewModel.setStatus("rejected");
            minedBlockStatusViewModel.setMessage("The block is rejected.");
            return minedBlockStatusViewModel;
        }

        // Is the block already mined
        if (this.node.getBlocks().get(this.node.getBlocks().size() - 1).getIndex() + 1
                != blockCandidate.getIndex()) {
            minedBlockStatusViewModel.setStatus("not-found");
            minedBlockStatusViewModel.setMessage("The block is rejected.");
            return minedBlockStatusViewModel;
        }

        blockCandidate.setNonce(minedBlockModel.getNonce());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        try {
            date = formatter.parse(minedBlockModel.getDateCreated());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        blockCandidate.setDateCreated(date);
        blockCandidate.setBlockHash(blockHash);

        this.node.addBlock(blockCandidate);
        // Reset new block meta
        this.node.setPendingTransactions(new ArrayList<>());
        this.node.setPendingTransactionsHashes(new HashSet<>());
        this.node.setMiningJobs(new HashMap<>());

        notifyPeersForNewBlock(blockCandidate);

        minedBlockStatusViewModel.setMessage("Block accepted, reward paid: " +
                blockCandidate.getTransactions().get(0).getValue() + " microcoins");
        minedBlockStatusViewModel.setStatus("accepted");

        return minedBlockStatusViewModel;
    }

    private void notifyPeersForNewBlock(Block blockCandidate) {
        NotifyBlockModel notifyBlockModel = new NotifyBlockModel();
        notifyBlockModel.setIndex(blockCandidate.getIndex());
        notifyBlockModel.setCumulativeDifficulty(this.nodeService.getNodeInfo().getCumulativeDifficulty());
        Peer peer = new Peer();
        peer.setUrl(getServerURL());
        notifyBlockModel.setPeer(peer);
        NodeUtils.notifyPeersForNewBlock(notifyBlockModel, this.node.getPeers());
    }
}
