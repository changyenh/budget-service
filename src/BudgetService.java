import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BudgetService {

    private final IBudgetRepo iBudgetRepo;
    private final Map<String, Double> dailyBudgetMap;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");


    public BudgetService(IBudgetRepo iBudgetRepo) {
        this.iBudgetRepo = iBudgetRepo;
        this.dailyBudgetMap = this.iBudgetRepo.getAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                b -> b.yearMonth,
                                b -> ((b.amount * 1.0) / YearMonth.parse(b.yearMonth, formatter).getMonth().maxLength())));

    }


    public double query(final LocalDate startTime, final LocalDate endTime) {
        int days = (int)ChronoUnit.DAYS.between(startTime, endTime) + 1;
        return IntStream.range(0, days).boxed().mapToDouble(i -> {
            LocalDate date = startTime.plusDays(i);
            return dailyBudgetMap.getOrDefault(formatter.format(date), 0.0);
        }).sum();
    }

}
