package tw.idv.rainbow.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.rainbow.web.entity.Campaigns;

import java.util.List;

public interface CampaignsRepository extends JpaRepository<Campaigns,Integer> {

    List<Campaigns> findByIsDeletedIsFalseOrderByCreateAtDesc();

    Campaigns findByCampaignId(Long campaignId);

    int deleteByCampaignId(Long campaignId);
}
