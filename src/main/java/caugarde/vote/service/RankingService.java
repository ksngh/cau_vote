package caugarde.vote.service;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.dto.response.RankingResponseDTO;
import caugarde.vote.model.entity.Ranking;
import caugarde.vote.repository.jpa.RankingRepository;
import caugarde.vote.repository.jpa.SemesterRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final SemesterRepository semesterRepository;
    private final RankingRepository rankingRepository;

    public List<Ranking> getAllRanking() {
        return rankingRepository.findAll();
    }

    public void saveRankings(List<Ranking> rankings) {
        rankingRepository.saveAll(rankings);
    }

    public List<RankingResponseDTO> rankingsToDTOs(List<Ranking> rankings) {
        return rankings.stream()
                .map(RankingResponseDTO::new)
                .toList();
    }

    public Map<String, List<RankingResponseDTO>> groupAndSortBySemester(List<RankingResponseDTO> rankingList) {
        return rankingList.stream()
                // 학기별로 그룹화
                .collect(Collectors.groupingBy(
                        RankingResponseDTO::getSemester,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        // 출석 수 내림차순 정렬
                                        .sorted(Comparator.comparingInt(RankingResponseDTO::getAttendance).reversed())
                                        .collect(Collectors.toList())
                        )
                ))
                // 학기 기준으로 오름차순 정렬
                .entrySet().stream()
                .sorted((entry1, entry2) -> SemesterUtil.compare(entry1.getKey(), entry2.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
