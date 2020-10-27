import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BudgetService {

  private final IBudgetRepo iBudgetRepo;
  private final Map<String, Double> budgetPerDay;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");


  public BudgetService(IBudgetRepo iBudgetRepo) {
    this.iBudgetRepo = iBudgetRepo;
    this.budgetPerDay = this.iBudgetRepo.getAll()
        .stream()
        .collect(Collectors.toMap(
            b -> b.yearMonth,
            b -> ((b.amount * 1.0) / parseMonthLength(b.yearMonth))));
  }

  private int parseMonthLength(String yearMonth) {
    return YearMonth.parse(yearMonth, formatter).getMonth().maxLength();
  }


  public double query(final LocalDate startTime, final LocalDate endTime) {
    int days = (int) ChronoUnit.DAYS.between(startTime, endTime) + 1;
    return IntStream.range(0, days).boxed().mapToDouble(i -> getBudget(startTime.plusDays(i))).sum();
  }

  private double getBudget(LocalDate date) {
    return budgetPerDay.getOrDefault(formatter.format(date), 0.0);
  }

}
