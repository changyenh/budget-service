import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

public class BudgetService {

    private IBudgetRepo iBudgetRepo;

    public BudgetService(IBudgetRepo iBudgetRepo) {
        this.iBudgetRepo = iBudgetRepo;
    }

    public double query(LocalDate startTime, LocalDate endTime) {
        List<Budget> budgets = iBudgetRepo.getAll();

        Period period = Period.between(startTime, endTime);

        int periodDays = period.getDays() + 1;
        if (periodDays >= 0) {
            LocalDate tempDate = LocalDate.from(startTime);
            double sum = 0.0;
            for (int i = 0; i <= periodDays; i++) {
                Optional<Budget> b=budgets.stream().filter(budget ->
                    Integer.parseInt(budget.yearMonth.substring(4, 6)) == startTime.getMonth().getValue() && Integer.parseInt(budget.yearMonth.substring(0, 4)) ==  startTime.getYear()
            ).findFirst();
                if (b.get() != null && b.get().amount > 0) {
                    sum+=b.get().amount / tempDate.getMonth().maxLength();
                }
                tempDate.plusDays(1);
            }
            return sum;
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
