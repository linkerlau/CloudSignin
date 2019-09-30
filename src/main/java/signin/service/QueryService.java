package signin.service;

import org.springframework.stereotype.Service;
import signin.model.ChartData;

import java.util.List;

@Service
public interface QueryService {
    List<ChartData> getNowHomeFixedPercentage();
    List<ChartData> getYesterdayHomeFixedPercentage();
    List<ChartData> getWeekHomeFixedPercentage();

    List<ChartData> getNowHomeFixedSituation();
    List<ChartData> getYesterdayHomeFixedSituation();

    List<ChartData> getHomeOptionalSituation(String firstDate, String secondDate);

    List<ChartData> getNowUserFixedPercentage(String username);
    List<ChartData> getYesterdayUserFixedPercentage(String username);
    List<ChartData> getWeekUserFixedPercentage(String username);

    List<ChartData> getWeekUserFixedSituation(String username);

    List<ChartData> getUserOptionalSituation(String username, String firstDate, String secondDate);
}
