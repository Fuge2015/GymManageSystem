# 羽毛球馆维护预定
负责场地的维护和预订工工作。为了了简化自自己己的工工作,场地只接受整点预订,预订以小小时为单位。

-------------------
##程序输入
###预订:用户预订以字符串的形式输入,一行字符串代表一个预定
- **格式为 `{用户ID} {预订日期 yyyy-MM-dd} {预订时间段 HH:mm~HH:mm} {场地} `,如 `U123 2016-06-02 20:00~22:00 A`,代表用户U123预定2016年06月02日晚上20:00到22:00的场地A的时间段**
- **时间段的起止时间必然为整小时,否则报错**
- **如预订与已有预订冲突,也会报错**
###取消预定:用户取消预定,输入也以一行字符串的形式表现
- **格式为`{用户ID} {预订日期 yyyy-MM-dd} {预订时间段 HH:mm~HH:mm} {场地} {取消标记}`,如 `U123 2016-06-02 20:00~22:00 A C` ,代表用户U123取消其在2016年06月02日晚上20:00到22:00在场地A的预订,其中取消标记C代表Cancel**
- **取消标记只能是C,若为其他字符则报错**
- **时间段的起止时间必然为整小时,否则报错**
- **只能完整取消之前的预订,不能取消部分时间段**
- **取消预订的请求,必须与之前的预订请求严格匹配,需要匹配的项有用用户ID,预订日日期,预订时间段,场地**
###打印场馆收入汇总: 将所有的预订和取消预订带来的收入汇总信息打印出来
- **格式为,输入一个空行,代表打印收入汇总**
###程序输出
- **收入汇总:以文本的形式输出当前系统所有预订以及取消预订所带来的收入情况,以不同的场地分组,一个可能的输出如下所示:**
>
>收入汇总
>---
>场地:A
>2016-06-02 09:00~10:00 违约金金金 15元
>2016-06-02 10:00~12:00 60元
>2016-06-03 20:00~22:00 120元
>小小计:195元
>
>场地:B
>2016-06-04 09:00~10:00 40元
>
>场地:C
>小小计:0元
>
>场地:D
>小小计:0元
>---
>总计: 235元

##测试
对部分方法进行了测试
###Item类的部分方法
- **public boolean equals(Object obj)：若两个Item的实例的用户、时间和场地相同则认为两条记录相同，则认为两个Item实例相等**
- **public boolean isConflict(Item item)：判断两条预定的时间和场地是否冲突**
- **public int compareTo(Item anotherItem)：判断两个对象的大小关系，并返回-1,0或者1**

###Utils类的部分方法
- **public static Item bookMessageToItem(String string)：将输入的预定信息或者取消预订信息转换为Item类的实例对象**
