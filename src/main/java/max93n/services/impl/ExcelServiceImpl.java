package max93n.services.impl;

import max93n.models.Role;
import max93n.models.User;
import max93n.models.enums.RoleEnum;
import max93n.services.ExcelService;
import max93n.services.RoleService;
import max93n.services.UserService;
import max93n.utils.exceptions.UserExistsException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service("excelService")
public class ExcelServiceImpl implements ExcelService{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public void importData(InputStream inputStream) {

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            importUserSheet(workbook);

            importRoleSheet(workbook);

            importUserRolesSheet(workbook);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void importUserRolesSheet(Workbook workbook) {
        Sheet sheet = workbook.getSheet("user_roles");

        Iterator<Row> rowIter = sheet.iterator();

        //skip first line of heads
        rowIter.next();

        while (rowIter.hasNext()) {
            Row row = rowIter.next();

            User user = userService.findById((long) row.getCell(0).getNumericCellValue());
            Role role = roleService.findById((long) row.getCell(1).getNumericCellValue());
            user.getUserRoles().add(role);

            userService.save(user);

        }

    }

    private void importRoleSheet(Workbook workbook) {

        Sheet sheet = workbook.getSheet("role");

        Iterator<Row> rowIter = sheet.iterator();

        //skip first line of heads
        rowIter.next();

        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            Role role = new Role();

            role.setId((long) row.getCell(0).getNumericCellValue());
            role.setRole(RoleEnum.valueOf(row.getCell(1).getStringCellValue()));

            roleService.add(role);
        }


    }

    private void importUserSheet(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIter = sheet.iterator();

        //skip first line of heads
        rowIter.next();

        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            User user = new User();
            user.setId((long)row.getCell(0).getNumericCellValue());
            user.setUsername(row.getCell(1).getStringCellValue());
            user.setPassword(row.getCell(2).getStringCellValue());
            user.setEmail(row.getCell(3).getStringCellValue());
            user.setRegistrationDate(row.getCell(4).getDateCellValue());

            Set<RoleEnum> roleEnums = new HashSet<>();
            roleEnums.add(RoleEnum.USER);
            try {
                userService.add(user, roleEnums);
            } catch (UserExistsException e) {}
        }

    }

    public void exportData() {

        Workbook workbook = new XSSFWorkbook();

        createUserSheet(workbook);

        createRoleSheet(workbook);

        createUserRoleSheet(workbook);

        FileOutputStream out = null;
        try {
             out = new FileOutputStream(new File("E:\\users.xlsx"));

            workbook.write(out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUserRoleSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("user_roles");

        String[] heads = new String[]{"user_id", "rold_id"};
        Row headRow = sheet.createRow(0);

        CellStyle headCellStyle = workbook.createCellStyle();
        Font headFont = workbook.createFont();
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setFontName("Arial");
        headCellStyle.setFont(headFont);

        for (int i = 0; i < heads.length; i++) {
            Cell headCell = headRow.createCell(i);
            headCell.setCellStyle(headCellStyle);
            headCell.setCellValue(heads[i]);
        }


        CellStyle dataCellStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataCellStyle.setFont(dataFont);

        int rowNum = 1;
        List<User> users = userService.getUsers();
        for (User user : users) {
            Set<Role> roles = user.getUserRoles();
            for (Role role : roles) {
                Row row = sheet.createRow(rowNum++);

                Cell userIdCell = row.createCell(0);
                userIdCell.setCellStyle(dataCellStyle);
                userIdCell.setCellValue(user.getId());

                Cell roleIdCell = row.createCell(1);
                roleIdCell.setCellStyle(dataCellStyle);
                roleIdCell.setCellValue(role.getId());
            }
        }
    }

    private void createUserSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Users");
        CreationHelper creationHelper = workbook.getCreationHelper();

        List<User> users = userService.getUsers();

        Row headRow = sheet.createRow(0);
        String[] heads = new String[]{"Id", "Username", "Password", "Email", "RegistrationDate"};


        CellStyle headCellStyle = workbook.createCellStyle();
        Font headFont = workbook.createFont();
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setFontName("Arial");
        headCellStyle.setFont(headFont);

        for (int i = 0; i < heads.length; i++) {
            Cell headCell = headRow.createCell(i);
            headCell.setCellStyle(headCellStyle);
            headCell.setCellValue(heads[i]);
        }

        CellStyle dataCellStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataCellStyle.setFont(dataFont);


        int rowNum = 1;
        for(User user : users) {
            Row row = sheet.createRow(rowNum++);

            Cell idCell = row.createCell(0);
            idCell.setCellStyle(dataCellStyle);
            idCell.setCellValue(user.getId());

            Cell usernameCell = row.createCell(1);
            usernameCell.setCellStyle(dataCellStyle);
            usernameCell.setCellValue(user.getUsername());

            Cell passwordCell = row.createCell(2);
            passwordCell.setCellStyle(dataCellStyle);
            passwordCell.setCellValue(user.getPassword());

            Cell emailCell = row.createCell(3);
            emailCell.setCellStyle(dataCellStyle);
            emailCell.setCellValue(user.getEmail());

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
            dateCellStyle.setFont(dataFont);
            Cell registrationDateCell = row.createCell(4);
            registrationDateCell.setCellValue(user.getRegistrationDate());
            registrationDateCell.setCellStyle(dateCellStyle);

        }

        for (int i = 0; i < heads.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    private void createRoleSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("role");
        List<Role> roles = roleService.getRoles();

        Row headRow = sheet.createRow(0);
        String[] heads = new String[]{"Role id", "Role"};


        CellStyle headCellStyle = workbook.createCellStyle();
        Font headFont = workbook.createFont();
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setFontName("Arial");
        headCellStyle.setFont(headFont);

        for (int i = 0; i < heads.length; i++) {
            Cell headCell = headRow.createCell(i);
            headCell.setCellStyle(headCellStyle);
            headCell.setCellValue(heads[i]);
        }

        CellStyle dataCellStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataCellStyle.setFont(dataFont);


        int rowNum = 1;
        for (Role role : roles) {
            Row row = sheet.createRow(rowNum++);

            Cell roleIdCell = row.createCell(0);
            roleIdCell.setCellStyle(dataCellStyle);
            roleIdCell.setCellValue(role.getId());

            Cell roleCell = row.createCell(1);
            roleCell.setCellStyle(dataCellStyle);
            roleCell.setCellValue(role.getRole().name());

        }

        for (int i = 0; i < heads.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }


}
