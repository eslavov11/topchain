package com.topchain.node.serviceImpl;

import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionViewModel getTransactionByFromAddress(String fromAddress) {
        return null;
    }
}
