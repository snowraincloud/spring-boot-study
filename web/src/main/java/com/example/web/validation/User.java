package com.example.web.validation;

import com.example.web.validation.annotation.ValidEnumValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;


/**
 * //TODO
 *
 * @author wangjunhao
 **/
@ApiModel("用户信息")
@Data
public class User {



    @NotNull(message = "ID不能为空", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    @Min(value = 1, message = "用户ID必须为正数", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    private Long id;

    @ApiModelProperty("姓名")
    @NotNull(message = "姓名不能为空", groups = ValidUserGroup.Add.class)
    @NotBlank(message = "姓名不能为空", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    private String name;

//    @NotNull(message = "性别不能为空", groups = ValidUserGroup.Add.class)
    @ValidEnumValues(values = {1,2},message = "性别只能传入1或者2", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    private Integer gender;

    @NotNull(message = "年龄不能为空", groups = ValidUserGroup.Add.class)
    @Range(min = 1,max = 120, message = "年龄必须在1到120之间", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    private Integer age;

//    @NotNull(message = "邮箱不能为空", groups = ValidUserGroup.Add.class)
    @Email(message = "邮箱不正确", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    private String email;

//    @NotNull(message = "出生日期不能为空", groups = ValidUserGroup.Add.class)
    @Past(message = "出生日期错误", groups = {ValidUserGroup.Add.class, ValidUserGroup.Update.class})
    private Date birthday;

}