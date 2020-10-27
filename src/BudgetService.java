import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetService {

    private IBudgetRepo iBudgetRepo;

    public BudgetService(IBudgetRepo iBudgetRepo) {
        this.iBudgetRepo = iBudgetRepo;
    }

    public double query(LocalDate startTime, LocalDate endTime) {
        List<Budget> budgets = iBudgetRepo.getAll();

        if (startTime.getMonth() == endTime.getMonth()) {

            List<Budget> monthBudgets = budgets.stream().filter(budget ->
                    Integer.parseInt(budget.yearMonth.substring(4, 6)) == startTime.getMonth().getValue() && Integer.parseInt(budget.yearMonth.substring(0, 4)) ==  startTime.getYear()
            ).collect(Collectors.toList());

            return monthBudgets.get(0).amount;
        }



        return 0.0d;
    }

    private List<Budget> filterBudget(LocalDate startTime, LocalDate endTime) {
        return null;
    }

    protected List<Budget> getReport() {
        return null;
    }
}
