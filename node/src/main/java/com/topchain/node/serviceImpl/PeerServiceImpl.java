package com.topchain.node.serviceImpl;

import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.PeerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PeerServiceImpl implements PeerService {
    ModelMapper modelMapper;

    @Autowired
    public PeerServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<PeerViewModel> getPeers() {
        return null;
    }

    @Override
    public ResponseMessageViewModel addPeer(PeerModel peerModel) {
        return new ResponseMessageViewModel("Added peer...");
    }
}
