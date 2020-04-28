package MVC;

public class MhsMVC {
    MhsView mhsview = new MhsView();
    MhsModel mhsmodel = new MhsModel();
    MhsDAO mhsDAO = new MhsDAO();
    MhsController mhscontroller = new MhsController(mhsmodel, mhsview, mhsDAO);
}
