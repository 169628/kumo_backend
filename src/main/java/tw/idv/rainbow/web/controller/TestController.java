package tw.idv.rainbow.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.repository.CampaignsRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private CampaignsRepository repository;

    @GetMapping("t1")
    public Optional<Campaigns> t1(){
        return repository.findById(8);
    }
}
