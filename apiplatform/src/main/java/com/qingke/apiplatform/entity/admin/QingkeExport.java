package com.qingke.apiplatform.entity.admin;

import com.qingke.apiplatform.model.Qingke;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author 蒋世芳
 * @Date 2018/11/23 1:45 PM
 */
@Data
public class QingkeExport extends Qingke
{

    String tax;
    int age;
    int expire;
    String taxAgent;
    String askAgent;
    String enterprisenName;
    String projectName;
    int projectId;

    public QingkeExport(Qingke qingke)
    {
        super.setQkidStatus(qingke.getQkidStatus());
        super.setQkNumb(qingke.getQkNumb());
        super.setQkVideoStatus(qingke.getQkVideoStatus());
        super.setQkCreate(qingke.getQkCreate());
        super.setQkidBackphotoUrl(qingke.getQkidBackphotoUrl());
        super.setQkidFrontphotoUrl(qingke.getQkidFrontphotoUrl());
        super.setQkLicenseStatus(qingke.getQkLicenseStatus());
        super.setQkOpenid(qingke.getQkOpenid());
        super.setQkPhone(qingke.getQkPhone());
        super.setQkSex(qingke.getQkSex());
        super.setQkSignStatus(qingke.getQkSignStatus());
        super.setQkVideoUrl(qingke.getQkVideoUrl());
        super.setQkBirth(qingke.getQkBirth());
        if(super.getQkBirth()!=null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(super.getQkBirth());
            int birthYear = calendar.get(Calendar.YEAR);
            calendar.setTime(new Date());
            age = calendar.get(Calendar.YEAR) - birthYear;
        }
        super.setQkCity(qingke.getQkCity());
        super.setQkComment(qingke.getQkComment());
        super.setQkCreditCode(qingke.getQkCreditCode());
        super.setQkId(qingke.getQkId());
        super.setQkidAddress(qingke.getQkidAddress());
        super.setQkidValidity(qingke.getQkidValidity());

        super.setQkLicenseUrl(qingke.getQkLicenseUrl());
        super.setQkName(qingke.getQkName());
        super.setQkNation(qingke.getQkNation());
        super.setQkSelfName(qingke.getQkSelfName());
        super.setQkSignUrl(qingke.getQkSignUrl());
        expire = caluterExpire();
    }

    private int caluterExpire()
    {
        int age = 0;
        try {
            if (super.getQkidValidity() != null) {
                String[] dates = super.getQkidValidity().split("-");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date startDate = sdf.parse(dates[0]);
                Date endDate = sdf.parse(dates[1]);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                int startYear = calendar.get(Calendar.YEAR);
                calendar.setTime(endDate);
                age = calendar.get(Calendar.YEAR) - startYear;
            }
        }
        catch (Exception ex)
        {

        }
        return  age;
    }

}
