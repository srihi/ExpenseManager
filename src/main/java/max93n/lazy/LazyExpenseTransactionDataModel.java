package max93n.lazy;

import max93n.entities.ExpenseTransaction;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.*;

public class LazyExpenseTransactionDataModel extends LazyDataModel<ExpenseTransaction> {

    private List<ExpenseTransaction> datasource;

    public LazyExpenseTransactionDataModel(List<ExpenseTransaction> datasource) {
        this.datasource = datasource;
    }

    @Override
    public ExpenseTransaction getRowData(String rowKey) {
        for (ExpenseTransaction expenseTransaction : datasource) {
            if (expenseTransaction.getId().equals(rowKey))
                return expenseTransaction;
        }

        return null;
    }

    @Override
    public Object getRowKey(ExpenseTransaction car) {
        return car.getId();
    }

    @Override
    public List<ExpenseTransaction> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                         Map<String, Object> filters) {
        List<ExpenseTransaction> data = new ArrayList<>();

        //filter
        for (ExpenseTransaction expenseTransaction : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(expenseTransaction.getClass().getField(filterProperty).get(expenseTransaction));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(expenseTransaction);
            }
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

}
