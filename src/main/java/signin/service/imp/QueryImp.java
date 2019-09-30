package signin.service.imp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.stereotype.Service;
import signin.dao.UserDaoFace;
import signin.model.ChartData;
import signin.model.Signin;
import signin.model.User;
import signin.service.QueryService;
import signin.utils.DayOfWeek;
import signin.utils.MyDateUtils;

import java.io.IOException;
import java.util.*;

@Service
public class QueryImp implements QueryService {

    private UserDaoFace userDaoFace;
    private Gson gson = new GsonBuilder().registerTypeAdapterFactory(
            new NullStringToEmptyAdapterFactory()
    ).create();

    public QueryImp(UserDaoFace userDaoFace) {
        this.userDaoFace = userDaoFace;
    }

    //当天
    @Override
    public List<ChartData> getNowHomeFixedPercentage() {
        List<User> users = userDaoFace.findAll();

        int allTimes = 0;
        for (User user : users) { //遍历所有用户
            Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
            for (Signin signin : signins) {     //遍历当前用户所有日期的签到信息
                if (MyDateUtils.getNowDayOfYear(signin.getDate()) == MyDateUtils.getNowDayOfYear(new Date())) { //确认获得当天签到信息
                    for (String s : signin.getSituation()) {
                        if (s.equals("1")) {
                            allTimes++;
                        }
                    }
                    break;
                }
            }
        }

        List<ChartData> chartDatas = new ArrayList<>();

        ChartData chartData1 = new ChartData();
        chartData1.setLabel("已签占比");
        chartData1.setData(allTimes / 2.0 * 100);

        ChartData chartData2 = new ChartData();
        chartData2.setLabel("未签占比");
        chartData2.setData(100 - allTimes / 2.0 * 100);

        chartDatas.add(chartData1);
        chartDatas.add(chartData2);

        return chartDatas;
    }

    //昨天
    @Override
    public List<ChartData> getYesterdayHomeFixedPercentage() {
        List<User> users = userDaoFace.findAll();
        int allTimes = 0;
        for (User user : users) { //遍历所有用户
            Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
            for (Signin signin : signins) {     //遍历当前用户所有日期的签到信息
                if (MyDateUtils.getNowDayOfYear(signin.getDate()) == MyDateUtils.getYesterdayDayOfYear(new Date())) { //确认获得昨天签到信息
                    for (String s : signin.getSituation()) {
                        if (s.equals("1")) {
                            allTimes++;
                        }
                    }
                    break;
                }
            }
        }

        List<ChartData> chartDatas = new ArrayList<>();

        ChartData chartData1 = new ChartData();
        chartData1.setLabel("已签占比");
        chartData1.setData(allTimes / 2.0 * 100);

        ChartData chartData2 = new ChartData();
        chartData2.setLabel("未签占比");
        chartData2.setData(100 - allTimes / 2.0 * 100);

        chartDatas.add(chartData1);
        chartDatas.add(chartData2);

        return chartDatas;
    }

    //本周
    @Override
    public List<ChartData> getWeekHomeFixedPercentage() {
        List<User> users = userDaoFace.findAll();
        int countUsers = userDaoFace.countAll();

        int allTimes = 0;
        for (User user : users) { //遍历所有用户
            Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
            for (Signin signin : signins) {     //遍历当前用户所有日期的签到信息
                if (MyDateUtils.getNowWeek(signin.getDate()) == MyDateUtils.getNowWeek(new Date())) { //确认获得本周签到信息
                    for (String s : signin.getSituation()) {
                        if (s.equals("1")) {
                            allTimes++;
                        }
                    }
                    break;
                }
            }
        }
        List<ChartData> chartDatas = new ArrayList<>();
        System.out.println("allTimes: " + allTimes);
        System.out.println("countUsers: " + countUsers);

        ChartData chartData1 = new ChartData();
        chartData1.setLabel("已签占比");
        chartData1.setData(allTimes / (14.0 * countUsers) * 100);

        ChartData chartData2 = new ChartData();
        chartData2.setLabel("未签占比");
        chartData2.setData(100 - allTimes / (14.0 * countUsers) * 100);

        chartDatas.add(chartData1);
        chartDatas.add(chartData2);

        return chartDatas;
    }

    @Override
    public List<ChartData> getNowHomeFixedSituation() {
        List<User> users = userDaoFace.findAll();
        List<ChartData> chartDatas = new ArrayList<>();
        for (User user : users) {
            ChartData chartData = new ChartData();
            Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
            for (Signin signin : signins) { //遍历签到情况
                int times = 0;
                if (MyDateUtils.getNowDayOfYear(signin.getDate()) == MyDateUtils.getNowDayOfYear(new Date())) { //查到当天
                    for (String s : signin.getSituation()) {
                        if (s.equals("1")) {
                            times++;
                        }
                    }
                    chartData.setLabel(user.getUsername());
                    chartData.setData(times);
                    break;
                } else {
                    chartData.setLabel(user.getUsername());
                    chartData.setData(times);
                }
            }
            chartDatas.add(chartData);
        }
        return chartDatas;
    }

    @Override
    public List<ChartData> getYesterdayHomeFixedSituation() {
        List<User> users = userDaoFace.findAll();
        List<ChartData> chartDatas = new ArrayList<>();
        for (User user : users) {
            Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
            ChartData chartData = new ChartData();
            for (Signin signin : signins) { //遍历签到情况
                int times = 0;
                if (MyDateUtils.getNowDayOfYear(signin.getDate()) == MyDateUtils.getYesterdayDayOfYear(new Date())) { //查到昨天
                    for (String s : signin.getSituation()) {
                        if (s.equals("1")) {
                            times++;
                        }
                    }
                    chartData.setLabel(user.getUsername());
                    chartData.setData(times);
                    break;
                } else {
                    chartData.setLabel(user.getUsername());
                    chartData.setData(times);
                }
            }
            chartDatas.add(chartData);
        }
        return chartDatas;
    }

    @Override
    public List<ChartData> getHomeOptionalSituation(String firstDate, String secondDate) {
        List<User> users = userDaoFace.findAll();
        List<ChartData> chartDatas = new ArrayList<>();
        for (User user : users) {
            int times = 0;
            Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
            ChartData chartData = new ChartData();
            for (Signin signin : signins) {
                //进入特定日期
                if (MyDateUtils.getNowDayOfYear(signin.getDate()) >= MyDateUtils.getDayOfYearWithOptionalDate(firstDate)) {
                    for (String s : signin.getSituation()) {
                        if (s.equals("1")) {
                            times++;
                        }
                    }
                    if (MyDateUtils.getNowDayOfYear(signin.getDate()) > MyDateUtils.getDayOfYearWithOptionalDate(secondDate)) {
                        break;
                    }
                }
            }
            chartData.setLabel(user.getUsername());
            chartData.setData(times);
            chartDatas.add(chartData);
        }
        return chartDatas;
    }

    @Override
    public List<ChartData> getNowUserFixedPercentage(String username) {
        User user = userDaoFace.findUserByUsername(username);

        int allTimes = 0;
        Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
        for (Signin signin : signins) {     //遍历当前用户所有日期的签到信息
            if (MyDateUtils.getNowDayOfYear(signin.getDate()) == MyDateUtils.getNowDayOfYear(new Date())) { //确认获得当天签到信息
                for (String s : signin.getSituation()) {
                    if (s.equals("1")) {
                        allTimes++;
                    }
                }
                break;
            }
        }

        List<ChartData> chartDatas = new ArrayList<>();

        ChartData chartData1 = new ChartData();
        chartData1.setLabel("已签占比");
        chartData1.setData(allTimes / 2.0 * 100);

        ChartData chartData2 = new ChartData();
        chartData2.setLabel("未签占比");
        chartData2.setData(100 - allTimes / 2.0 * 100);

        chartDatas.add(chartData1);
        chartDatas.add(chartData2);

        return chartDatas;
    }

    @Override
    public List<ChartData> getYesterdayUserFixedPercentage(String username) {
        User user = userDaoFace.findUserByUsername(username);
        int allTimes = 0;
        Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
        for (Signin signin : signins) {     //遍历当前用户所有日期的签到信息
            if (MyDateUtils.getNowDayOfYear(signin.getDate()) == MyDateUtils.getYesterdayDayOfYear(new Date())) { //确认获得昨天签到信息
                for (String s : signin.getSituation()) {
                    if (s.equals("1")) {
                        allTimes++;
                    }
                }
                break;
            }
        }

        List<ChartData> chartDatas = new ArrayList<>();

        ChartData chartData1 = new ChartData();
        chartData1.setLabel("已签占比");
        chartData1.setData(allTimes / 2.0 * 100);

        ChartData chartData2 = new ChartData();
        chartData2.setLabel("未签占比");
        chartData2.setData(100 - allTimes / 2.0 * 100);

        chartDatas.add(chartData1);
        chartDatas.add(chartData2);

        return chartDatas;
    }

    @Override
    public List<ChartData> getWeekUserFixedPercentage(String username) {
        User user = userDaoFace.findUserByUsername(username);

        int allTimes = 0;
        Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
        for (Signin signin : signins) {     //遍历当前用户所有日期的签到信息
            if (MyDateUtils.getNowWeek(signin.getDate()) == MyDateUtils.getNowWeek(new Date())) { //确认获得本周签到信息
                for (String s : signin.getSituation()) {
                    if (s.equals("1")) {
                        allTimes++;
                    }
                }
                break;
            }
        }
        List<ChartData> chartDatas = new ArrayList<>();

        ChartData chartData1 = new ChartData();
        chartData1.setLabel("已签占比");
        chartData1.setData(allTimes / 14.0 * 100);

        ChartData chartData2 = new ChartData();
        chartData2.setLabel("未签占比");
        chartData2.setData(100 - allTimes / 14.0 * 100);

        chartDatas.add(chartData1);
        chartDatas.add(chartData2);

        return chartDatas;
    }

    @Override
    public List<ChartData> getWeekUserFixedSituation(String username) {
        User user = userDaoFace.findUserByUsername(username);
        Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
        List<ChartData> chartDatas = new ArrayList<>();

        int dayOfWeek = MyDateUtils.getFirstDayOfWeek(new Date()) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        for (Signin signin : signins) { //遍历签到情况
            //获取到周一的日期
            if (MyDateUtils.getNowDayOfYear(signin.getDate()) >= MyDateUtils.getDayOfYearOnMon(new Date(), dayOfWeek)) {
                ChartData chartData = new ChartData();
                int times = 0;
                for (String s : signin.getSituation()) {
                    if (s.equals("1")) {
                        times++;
                    }
                }
                chartData.setLabel(DayOfWeek.getName(MyDateUtils.getFirstDayOfWeek(signin.getDate())-1));//周几
                chartData.setData(times);
                chartDatas.add(chartData);
            }
        }

        for (int i = 0; i < 7; i++) {
            ChartData chartData = new ChartData();
            //如果某天未签到，则插入
            if (chartDatas.size() <= i || !chartDatas.get(i).getLabel().equals(DayOfWeek.getName(i+1))) {
                chartData.setData(0);
                chartData.setLabel(DayOfWeek.getName(i+1));
                chartDatas.add(i, chartData);
            }
        }

        return chartDatas;
    }

    @Override
    public List<ChartData> getUserOptionalSituation(String username, String firstDate, String secondDate) {
        User user = userDaoFace.findUserByUsername(username);
        List<ChartData> chartDatas = new ArrayList<>();

        int times = 0;
        Signin[] signins = gson.fromJson(user.getSituation(), Signin[].class);
        ChartData chartData = new ChartData();
        for (Signin signin : signins) {
            if (MyDateUtils.getNowDayOfYear(signin.getDate()) >= MyDateUtils.getDayOfYearWithOptionalDate(firstDate)) {
                for (String s : signin.getSituation()) {
                    if (s.equals("1")) {
                        times++;
                    }
                }
                if (MyDateUtils.getNowDayOfYear(signin.getDate()) > MyDateUtils.getDayOfYearWithOptionalDate(secondDate)) {
                    break;
                }
            }
            chartData.setLabel(signin.getDate().toString());
            chartData.setData(times);
            chartDatas.add(chartData);
        }
        return chartDatas;
    }


}

class StringAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return "";
        }
        return in.nextString();
    }
}

class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class) {
            return null;
        }
        return (TypeAdapter<T>) new StringAdapter();
    }
}