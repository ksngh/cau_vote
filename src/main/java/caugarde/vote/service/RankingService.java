package caugarde.vote.service;

import caugarde.vote.model.entity.redis.CachedRanking;
import caugarde.vote.repository.jpa.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;



}
