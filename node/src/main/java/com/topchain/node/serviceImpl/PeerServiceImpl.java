package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Node;
import com.topchain.node.entity.Peer;
import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.PeerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PeerServiceImpl implements PeerService {
    private ModelMapper modelMapper;
    private Node node;

    @Autowired
    public PeerServiceImpl(ModelMapper modelMapper, Node node) {
        this.modelMapper = modelMapper;
        this.node = node;
    }

    @Override
    public Set<PeerViewModel> getPeers() {
        Set<PeerViewModel> peerViewModels = new HashSet<>();
        this.node.getPeers().forEach((p) -> {
            PeerViewModel peerViewModel = this.modelMapper.map(p, PeerViewModel.class);
            peerViewModels.add(peerViewModel);
        });

        return peerViewModels;
    }

    @Override
    public ResponseMessageViewModel addPeer(PeerModel peerModel) {
        Peer peer = this.modelMapper.map(peerModel, Peer.class);

        if (this.node.getPeers().stream()
                .filter(p -> p.getUrl().equals(peer.getUrl())).count() != 0) {
            ResponseMessageViewModel responseMessageViewModel =
                    new ResponseMessageViewModel("Peer already present...");
            responseMessageViewModel.setExists(true);
            return responseMessageViewModel;
        }

        this.node.addPeer(peer);

        return new ResponseMessageViewModel("Added peer...");
    }
}
