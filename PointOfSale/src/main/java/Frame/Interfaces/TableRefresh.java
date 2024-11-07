package Frame.Interfaces;

import Entity.Product;

public interface TableRefresh {

    void refreshTableData();

    void setEnabled(boolean bln);

    boolean isPaymentValid(Product product);

    void refreshTable();
}
