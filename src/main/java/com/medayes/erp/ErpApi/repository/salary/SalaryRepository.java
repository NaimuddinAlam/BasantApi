package com.medayes.erp.ErpApi.repository.salary;

import com.medayes.erp.ErpApi.dto.request.salary.SalaryProcessRequest;
import com.medayes.erp.ErpApi.dto.response.salary.CompanyResponse;
import com.medayes.erp.ErpApi.dto.response.salary.SalaryEmployeeResponse;
import com.medayes.erp.ErpApi.dto.response.salary.SalaryHeadResponse;
import com.medayes.erp.ErpApi.dto.response.salary.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SalaryRepository {

    private SimpleJdbcCall simpleJdbcCall;

    private final JdbcTemplate jdbcTemplate;


    @PostConstruct
    private void init() {

        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PAY_SALRY_PROCESS")
                .withoutProcedureColumnMetaDataAccess()

                .declareParameters(

                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PBRANCH_CODE", Types.VARCHAR),

                        new SqlParameter("PEMP_TYPE", Types.VARCHAR),
                        new SqlParameter("PEMP_GROUP", Types.VARCHAR),
                        new SqlParameter("PDEPT_CODE", Types.VARCHAR),
                        new SqlParameter("PEMP_CODE", Types.VARCHAR),

                        new SqlParameter("PATTAN_MONTH", Types.VARCHAR),
                        new SqlParameter("puser_code", Types.VARCHAR),

                        new SqlParameter("PEXTRA", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR)
                )

                // Result Set 1
                .returningResultSet(
                        "company",
                        (rs, rowNum) -> mapCompany(rs)
                )

                // Result Set 2
                .returningResultSet(
                        "employee",
                        (rs, rowNum) -> mapEmployee(rs)
                )

                // Result Set 3
                .returningResultSet(
                        "earnings",
                        (rs, rowNum) -> mapSalaryHead(rs)
                )

                // Result Set 4
                .returningResultSet(
                        "deductions",
                        (rs, rowNum) -> mapSalaryHead(rs)
                )

                // Result Set 5
                .returningResultSet(
                        "salaryHeads",
                        (rs, rowNum) -> mapSalaryHead(rs)
                );
    }

    private CompanyResponse mapCompany(ResultSet rs)
            throws SQLException {

        return CompanyResponse.builder()
                .centCode(rs.getString("CENT_CODE"))
                .centName(rs.getString("CENT_NAME"))
                .add1(rs.getString("ADD1"))
                .add2(rs.getString("ADD2"))
                .cityCode(rs.getString("CITY_CODE"))
                .stateCode(rs.getString("STATE_CODE"))
                .countryCode(rs.getString("COUNTRY_CODE"))
                .postCode(rs.getString("POST_CODE"))
                .compName(rs.getString("COMP_NAME"))
                .build();
    }
    private SalaryEmployeeResponse mapEmployee(ResultSet rs)
            throws SQLException {

        return SalaryEmployeeResponse.builder()

                .compCode(rs.getString("COMP_CODE"))
                .centCode(rs.getString("CENT_CODE"))
                .empCode(rs.getString("EMP_CODE"))
                .structureCode(rs.getString("STRUCTURE_CODE"))

                .payMonth(rs.getString("PAY_MONTH"))
                .payableDays(rs.getString("PAYABLE_DAYS"))
                .salaryDate(rs.getString("SALARY_DATE"))

                .pfEmployer(rs.getString("PF_EMPLOYER"))
                .esicEmployer(rs.getString("ESIC_EMPLOYER"))

                .empName(rs.getString("EMP_NAME"))
                .desig(rs.getString("DESIG"))
                .department(rs.getString("DEPARTMENT"))

                .dob(rs.getString("DOB"))
                .doj(rs.getString("DOJ"))

                .gradeName(rs.getString("GRADE_NAME"))
                .locName(rs.getString("LOC_NAME"))

                .bankAccNo(rs.getString("BANK_ACC_NO"))
                .pfNo(rs.getString("PF_NO"))
                .panNo(rs.getString("PAN_NO"))
                .uanNo(rs.getString("UAN_NO"))
                .esicNo(rs.getString("ESIC_NO"))
                .bankName(rs.getString("BANK_NAME"))


                .lName("Name:")

                .lEmpNo("Employee No:")

                .lJobDate("Joining Date:")

                .lBankName("Bank Name:")

                .lDesignation("Designation:")

                .lBankAccountNo("Bank Account No:")
                .lDepartment("Department:")


                .lPANNumber("PAN Number:")

                .lLocation("Location:")
                .lPFNO("PF NO:")
                .lEffectiveWorkDays("Effective Work Days:")
                .lPFUAN("PF UAN:")
                .lLOP("LOP:")




                .build();
    }
    private SalaryHeadResponse mapSalaryHead(ResultSet rs)
            throws SQLException {

        return SalaryHeadResponse.builder()

                .compCode(rs.getString("COMP_CODE"))
                .centCode(rs.getString("CENT_CODE"))
                .empCode(rs.getString("EMP_CODE"))

                .payableDays(getStringSafe(rs, "PAYABLE_DAYS"))
                .payMonth(getStringSafe(rs, "PAY_MONTH"))

                .salHeadCode(getStringSafe(rs, "SAL_HEAD_CODE"))
                .salAmount(getStringSafe(rs, "SAL_AMOUNT"))
                .arrears(getStringSafe(rs, "ARREARS"))

                .build();
    }
    private String getStringSafe(
            ResultSet rs,
            String columnName) {

        try {
            return rs.getString(columnName);
        } catch (SQLException e) {
            return null;
        }
    }
    public SalaryResponse apiCalling(
            SalaryProcessRequest salaryProcessRequest) {

        Map<String, Object> parameters =
                map(salaryProcessRequest);

        Map<String, Object> results =
                simpleJdbcCall.execute(parameters);

        List<CompanyResponse> companyList =
                (List<CompanyResponse>) results.get("company");

        List<SalaryEmployeeResponse> employeeList =
                (List<SalaryEmployeeResponse>) results.get("employee");

        List<SalaryHeadResponse> earnings =
                (List<SalaryHeadResponse>) results.get("earnings");

        List<SalaryHeadResponse> deductions =
                (List<SalaryHeadResponse>) results.get("deductions");


        CompanyResponse company =
                companyList != null && !companyList.isEmpty()
                        ? companyList.get(0)
                        : null;

        SalaryEmployeeResponse employee =
                employeeList != null && !employeeList.isEmpty()
                        ? employeeList.get(0)
                        : null;


        return SalaryResponse.builder()
                .company(company)
                .employee(employee)
                .earnings(
                        earnings != null
                                ? earnings
                                : List.of()
                )
                .deductions(
                        deductions != null
                                ? deductions
                                : List.of()
                )
                .build();
    }

    private Map<String, Object> map(
            SalaryProcessRequest salaryProcessRequest) {

        Map<String, Object> map =
                new LinkedHashMap<>();

        map.put(
                "PCOMP_CODE",
                salaryProcessRequest.getPcompCode()
        );

        map.put(
                "PCENT_CODE",
                salaryProcessRequest.getPcentCode()
        );

        map.put(
                "PBRANCH_CODE",
                salaryProcessRequest.getPbranchCode()
        );

        map.put(
                "PEMP_TYPE",
                salaryProcessRequest.getPempType()
        );

        map.put(
                "PEMP_GROUP",
                salaryProcessRequest.getPempGroup()
        );

        map.put(
                "PDEPT_CODE",
                salaryProcessRequest.getPdeptCode()
        );

        map.put(
                "PEMP_CODE",
                salaryProcessRequest.getPempCode()
        );

        map.put(
                "PATTAN_MONTH",
                salaryProcessRequest.getPattanMonth()
        );

        map.put(
                "puser_code",
                salaryProcessRequest.getPuserCode()
        );

        map.put(
                "PEXTRA",
                salaryProcessRequest.getPextra()
        );

        map.put(
                "PEXTRA1",
                salaryProcessRequest.getPextra1()
        );

        map.put(
                "PEXTRA2",
                salaryProcessRequest.getPextra2()
        );

        map.put(
                "PEXTRA3",
                salaryProcessRequest.getPextra3()
        );

        map.put(
                "PEXTRA4",
                salaryProcessRequest.getPextra4()
        );

        map.put(
                "PEXTRA5",
                salaryProcessRequest.getPextra5()
        );

        return map;
    }
}