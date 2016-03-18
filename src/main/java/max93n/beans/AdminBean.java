package max93n.beans;


import max93n.models.User;
import max93n.services.ExcelService;
import max93n.services.UserService;
import max93n.services.impl.UserServiceImpl;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@ManagedBean
@SessionScoped
public class AdminBean {

    @ManagedProperty("#{userService}")
    private UserService userService;

    @ManagedProperty("#{excelService}")
    private ExcelService excelService;

    private User user;

    private StreamedContent file;

    private UploadedFile uploadedFile;


    public List<User> getUsers() {
        return userService.getUsers();
    }

    public void remove(User user) {
        userService.remove(user);
    }

    public void edit() {
        userService.save(user);
    }



    public StreamedContent getFile() {
        exportData();
        return file;
    }

    public void importData() {

        if(uploadedFile == null || uploadedFile.getSize() <= 0) {
             return;
        }

        try {
            excelService.importData(uploadedFile.getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void exportData() {
        excelService.exportData();
        InputStream stream;
        try {
            stream = new FileInputStream("E:\\users.xlsx");
            file = new DefaultStreamedContent(stream, "application/octet-stream", "users.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExcelService getExcelService() {
        return excelService;
    }

    public void setExcelService(ExcelService excelService) {
        this.excelService = excelService;
    }


    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
