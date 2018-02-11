package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.viewModel.BalanceViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private ModelMapper modelMapper;
    private Node node;

    @Autowired
    public TransactionServiceImpl(ModelMapper modelMapper, Node node) {
        this.modelMapper = modelMapper;
        this.node = node;
    }

    @Override
    public TransactionViewModel getTransactionByFromAddress(String fromAddress) {
//        Transaction transaction = this.node.

        return null;
    }

    @Override
    public BalanceViewModel getBalanceByAddressForConfirmations(String address, int confirmations) {
        return null;
    }
}
