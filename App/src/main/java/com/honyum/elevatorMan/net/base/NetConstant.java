
package com.honyum.elevatorMan.net.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetConstant {


    public static final String UPDATEWORKORDER= "/workOrder/updateWorkOrder";
    public static final String GETINDEXWEIXIUGONGLINE= "/getIndexWeiXiuGongLine";
    public static final String GETINDEXALARMREPORTELEVATORLINE= "/getIndexAlarmReportElevatorLine";
    public static final String GETINDEXBAOXIUREPORTELEVATORLINE= "/getIndexBaoXiuReportElevatorLine";
    public static final String GETINDEXWORKORDERREPORTELEVATORLINE= "/getIndexWorkOrderReportElevatorLine";


    public static final String GETINDEXBAOXIUREPORT= "/getIndexBaoXiuReport";
    public static final String GETINDEXWORKORDERREPORT= "/getIndexWorkOrderReport";
    public static final String GETINDEXALARMREPORT= "/getIndexAlarmReport";
    public static final String GETINDEXKPI= "/getIndexKPI";
    public static final String WORKORDERGETWORKORDERCOUNTBYCOMMUNITY= "/workOrder/getWorkOrderCountByCommunity";


    public static final String REDISTRIBUTE= "/workOrder/reDistribute";

    public static final String GETCONTRACTPAYMENTINFOBYID= "/getContractPaymentInfoById";
    public static final String GETUNREAD= "/getRedHint";

    /**
     * 报修流程确认
     */
    public static final String GETSTATSTATE = "/workOrder/getStatState";
    /**
     * 报修流程确认
     */
    public static final String GETMAINPLAN = "/getMainPlanInfoById";
    /**
     * 报修流程确认
     */
    public static final String COMMITMAINPLAN = "/commitMainPlan";
    /**
     * 报修流程确认
     */
    public static final String GETBAOXIULISTBYSTATE = "/getBaoxiuListByState";

//    /**
//     * 报修流程确认
//     */
//    public static final String GETMAINTLISTBYBRANCHID = "/getMaintListByBranchId";

    /**
     * 报修流程确认
     */
    public static final String CONFIRM_BAOXIU_PROCESS = "/confirmBaoxiuProcess";

    /**
     * 根据电梯编号获取电梯详情
     */
    public static final String GET_ELEVATOR_BY_ELEVATOR_ID = "/getElevatorByElevatorId";
    /**
     * 报修流程处理
     */
    public static final String GETBAOXIUDETAILBYID = "/getBaoxiuDetailById";
    /**
     * 报修流程处理
     */
    public static final String ASSIGNBAOXIUUSER = "/assignBaoxiuUser";

    /**
     * 报修流程处理
     */
    public static final String GETUSERLISTBYBAOXIUID = "/getUserListByBaoxiuId";
    /**
     * 获取保修单
     */
    public static final String UNDEALCOUNT = "/undealCount";
    /**
     * 获取保修单
     */
    public static final String GET_BAOXIU_LIST = "/workOrder/getBaoxiuListByRepair";
    /**
     * 获取维保内容
     */
    public static final String GET_MAINT_ITEM= "/workOrder/getMaintItemByOrderId";
    /**
     * 获取故障类型
     */
    public static  final  String GET_FAULT_TYPE = "/getFaultTypeByCode";


    /**
     * 处理报修工单
     */
    public static final String SUCCESS_BAOXIU_WORK_ORDER =  "/workOrder/successBaoxiuWorkOrder";

    /**
     * 处理维保工单
     */
    public static final String SUCCESS_MAINT_WORK_ORDER =  "/workOrder/successMaintWorkOrder";
    /**
     * 获取维保工单
     */
    public static  final  String GET_WORK_ORDERS_BY_REPAIR="/workOrder/getWorkOrdersByRepair";

    /**
     * 取消工单
     */
    public static final String CANCEL_WORK_ORDER = "/workOrder/cancelWorkOrder";

    /**
     * 获取维保订单列表
     */
    public static final String GET_MAINT_LIST_BY_REPAIR = "/workOrder/getMaintListByRepair";

    /**
     * 获取合同详情
     */
    public static final String GET_CONTRACT = "/workOrder/getContract";

    /**
     * 添加维保工单
     */
    public static final String EDIT_WORK_ORDER = "/workOrder/editWorkOrderDate";
    /**
     * 添加维保工单
     */
    public static final String ADD_WORK_ORDER = "/workOrder/addWorkOrder";


    /**
     * 获取同意、不同意
     */
    public static final String GETAPPROVEHISTORYDATAGRID = "/getApproveHistoryDataGrid";

    /**
     * 获取同意、不同意
     */
    public static final String GETPROCESSPATH = "/getProcessPath";

    /**
     * 获取联系人列表
     */
    public static final String SELECTUSERDATAGRID = "/selectUserDataGrid";
    /**
     * 提交下一流程
     */
    public static final String COMMITNEXTPROCESSNODE = "/commitNextProcessNode";

    /**
     * 获取电梯故障类型
     */
    public static final String GET_REPAIR_TYPE_LIST = "/getFaultType";

    /**
     * 报修管理列表
     */
    public static final String GET_BAOXIU_BY_ROIE_ID = "/getBaoxiuByRoleId";

    /**
     * 报修管理列表
     */
    public static final String ADD_BAOXIU = "/addBaoxiu";

    /**
     * 获取电梯信息
     */
    public static final String GET_ELEVATOR_LIFT_NUM = "/getElevatorByLiftNumAndNumber";

    /**
     * 报修流程处理
     */
    public static final String SAVE_BAOXIU_PROCESS = "/saveBaoxiuProcess";
    /**
     * 代办列表
     */
    public static final String UNDEALDATAGRID = "/undealDataGrid";
    /**
     * 获取合同列表
     */
    public static final String GET_CONTRACT_BY_ROLEID = "/getContractByRoleId";

    /**
     * 获取合同详情
     */
    public static final String GET_CONTRACT_INFO_BY_ID = "/getContractInfoById";

    /**
     * 保险条款
     *
     */

    public static final String GET_CHATUSERBYALARMID = "/getChatUserByAlarmId";


    /**
     * 保险条款
     *
     */

    public static final String COMPANY_APPLY = "/h5/regProtocol";
    /**
     *  根据项目ID获取电梯信息
     */
    public static final String GET_ELEVATOR_BY_COMMUNITYID = "/getElevatorByCommunityID";
    /**
     *  根据距离获取项目信息
     */
    public static final String GET_COMMUNITY_BY_DISTANCE = "/getCommunityByDistance";
    /**
     *  获取扫码信息错误类型
     */
    public static final String EDIT_ERROR_REASON = "/editElevatorError";

    /**
     *  获取救援故障类型
     */
    public static final String TOP_FAULT_TYPE = "/getTopFaultType";
    /**
     *  获取扫码信息错误类型
     */
    public static final String ERROR_REASON = "/getErrorReason";
    /**
     * 根据识别编码获取电梯信息
     */
    public static final String ELE_RECORD_BYID = "/getElevatorByLiftNum";


    /**
     * 根据识别编码获取电梯信息
     */
    public static final String ELE_RECORD_UPDATE = "/editElevatorPosition";
    /**
     * 签到、签退、请假
     */
    public static final String SIGN = "/addAttendance";
    /**
     * 销假
     */

    public static final String SIGN_CANCEL = "/editAttendance";
    /**
     * 销假
     */

    public static final String SIGN_GET = "/getAttendance";


    /**
     * 上传log文件
     */

    public static final String UPLOAD_FILE = "/uploadFile";

    /**
     * 查询企业申请状态
     */

    public static final String GET_POLICY = "/getPolicyUserByUserInfo";
    /**
     * 查询企业申请状态
     */

    public static final String UPDATE_APPLIY = "/editAppliy";

    /**
     * 查询企业申请状态
     */

    public static final String GET_APPLIY = "/getAppliyByuserId";
    /**
     * 添加企业申请
     */

    public static final String ADD_APPLY = "/addAppliy";
    /**
     * 获取验证码
     */

    public static final String SMS_CODE = "/getSMSCodeBySmallOwner";



    /**
     * 提交保险信息
     */
    public static final String EDIT_INSURANCE = "/editInsuranceByUser";


    /**
     * 提交保险信息
     */
    public static final String GET_DISTANCE = "/getReachDistanceBybranch";



    /**
     * 获取保险人员信息
     */
    public static final String GET_PERSON_INFO = "/getUserById";

    /**
     * 获取在线人员列表
     */
    public static final String GET_PERSONS_LIST = "/getUserWorkBybranch";

    /**
     * 获取在线人数
     */
    public static final String GET_PERSONS = "/getUserWorkOnLineCountBybranch";


    /**
     * e梯配
     */
    public static final String EASY_LADDER_ORDERBYUSER = "/getEasyLadderOrderByUser";
    /**
     * e梯配
     */
    public static final String EASY_LADDER_URL = "/getEasyLadderUrl";
    /**
     * 维修公司提交任务完成状态
     */
    public static final String PROPERTY_CONFIRM = "/propertyConfirmComplete";
    /**
     * 上传音频
     */
    public static final String UPLOAD_VIDEO = "/uploadVideo";
    /**
     * 获取广告条
     */
    public static final String YI_ZHU = "/h5/yiZhuIndexPage";

    /**
     * 获取广告条
     */
    public static final String NY_YI_ZHU = "/h5/myYiZhuPage";

    /**
     * 注册信息
     */
    public static final String REG_COMPANY = "/registerMaintUser";

    /**
     * 获取广告条
     */
    public static final String GET_BANNER = "/getAdvertisementBySmallOwner";
    /**
     * 电梯常识
     */
    public static final String GET_KNOWLEDGE_BYKNTYPE = "/getKnowledgeByKntype";
    /**
     * 返回成功
     */
    public static final String RSP_CODE_SUC_0 = "0";

    /**
     * 服务器重启，导致登陆超时
     */
    public static final String RSP_CODE_TIME_OUT = "-2";

    /**
     * 其他人登陆
     */
    public static final String RSP_CODE_OTHER_LOGIN = "-3";

    /**
     * @Fields login:
     */
    public static final String URL_LOG_IN = "/login";

    /**
     * 请求报警信息
     */
    public static final String URL_ALARM_INFO = "/getAlarmDetail";
    /**
     * 上传位置信息
     */
    public static final String URL_PROCESS_ALARM = "/getProcessByAlarmId";

    /**
     * 上传位置信息
     */
    public static final String URL_REPORT_LOCATION = "/updateLocation";

    /**
     * 接受任务
     */
    public static final String URL_ACCEPT_ALARM = "/userAcceptAlarm";

    /**
     * 上报状态
     */
    public static final String URL_REPORT_STATE = "/saveProcessRecord";


    /**
     * 物业请求最近的一条报警信息
     */
    public static final String URL_ALARM_LIST_ONE = "/getOneAlarmListByUserId";

    /**
     * 物业请求报警信息列表
     */
    public static final String URL_ALARM_LIST = "/getAlarmListByUserId";

    /**
     * 维修工请求报警信息列表
     */
    public static final String URL_WORKER_ALARM_LIST = "/getAlarmListByRepairUserId";

    /**
     * 物业请求项目列表
     */
    public static final String URL_PROJECT_LIST = "/getElevatorList";

    /**
     * 物业报警
     */
    public static final String URL_REPORT_ALARM = "/alarm";

    /**
     * 物业请求报警状态
     */
    public static final String URL_ALARM_STATE = "/getRepairListByAlarmId";

    /**
     * 维修工途中遇到意外情况上报
     */
    public static final String URL_WORKER_EXCEPT = "/unexpectedByUser";

    /**
     * 物业确认报警救援完成
     */
    public static final String URL_CONFIRM_ALARM = "/propertyConfirmComplete";

    /**
     * 版本检测接口
     */
    public static final String URL_VERSION_CHECK = "/checkVersion";


    /**
     * 退出登录
     */
    public static final String URL_LOG_OUT = "/logout";

    /**
     * 请求报警地点周围维修工位置
     */
    public static final String URL_WORKERS = "/getNearUserLocation";

    /**
     * 上报时间节点
     */
    public static final String URL_REPORT_TIME = "/online";
    /**
     * 请求维保电梯信息
     */
    public static final String URL_GET_LIFT_INFO1 = "/getMaintenanceByElevatorId";
    /**
     * 请求维保电梯信息
     */
    public static final String URL_GET_LIFT_INFO = "/getMainElevatorListBJ";

    /**
     * 请求维保电梯信息
     */
    public static final String URL_GET_LIFT_INFO2 = "/getMainElevatorList";

    /**
     * 上报维保结果
     */
    public static final String URL_REPORT_MAIN = "/finishMain";

    /**
     * 上报维保计划
     */
    public static final String URL_REPORT_PLAN = "/newNewMainPlan";
    /**
     * 获取临时维保计划
     */
        public static final String MAIN_PLAN_CACHE = "/mainPlanCache";

    /**
     * 修改维保计划
     */
    public static final String URL_MODIFY_PLAN = "/updateMainPlan";

    /**
     * 删除维保计划
     */
    public static final String URL_DELETE_PLAN = "/removeMainPlan";

    /**
     * 维修工注册
     */
    public static final String URL_WORKER_REGISTER = "/registerRepair";

    /**
     * 物业获取维保电梯计划列表
     */
    public static final String URL_PRO_PLAN_LIST = "/getMainPlanByPropertyId";

    /**
     * 物业上报维保计划处置结果
     */
    public static final String URL_PRO_REPORT_PLAN_RESULT = "/verifyMainPlan";

    /**
     * 物业获取维保完成待确认的电梯列表
     */
    public static final String URL_PRO_GET_FINISH_RESULT = "/getFinishedMainList";

    /**
     * 物业获取维保历史
     */
    public static final String URL_PRO_GET_MAIN_HISTORY = "/getHistoryMainList";

    /**
     * 物业获取维保详情
     */
    public static final String URL_PRO_GET_MAIN_DETAIL = "/getMainDetail";

    /**
     * 修改个人信息
     */
    public static final String URL_MODIFY_INFO = "/editUser";

    /**
     * 密码修改
     */
    public static final String URL_PWD_MODIFY = "/editUserPwd";

    /**
     * 上传头像
     */
    public static final String URL_UPLOAD_ICON = "/updateLoadPic";


    /**
     * 请求维保公司列表
     */
    public static final String URL_REQUEST_COMPANY = "/getBranchs";

    /**
     * 重置密码接口
     */
    public static final String URL_RESET_PWD = "/resetPWD";

    /**
     * 获取汉邦一点通视频参数信息
     */
    public static final String URL_REQUEST_YDT = "/getYdtParam";

    /**
     * 接收到通知后的反馈接口
     */
    public static final String URL_NOTIFY_FEEDBACK = "/alarmFeedback";


    public static final String URL_ALARM_UNASSIGNED = "/getAlarmListByReceiveAndUnassign";


    /**
     * 上报工作地点
     */
    public static final String URL_REPORT_WORK_PLACE = "/saveOrUpdateResidentAddress";


    /**
     * 上报家庭地址
     */
    public static final String URL_REPORT_HOME_PLACE = "/saveOrUpdateFamilyAddress";


    /**
     * 上传音频
     */
    public static final String UPLOAD_AUDIO = "/uploadAudio";


    /**
     * 添加聊天记录
     */
    public static final String SEND_CHAT = "/addChat";


    /**
     * 获取聊天记录
     */
    public static final String GET_CHAT_LIST = "/getChatList";


    /**
     * 上传图片
     */
    public static final String UPLOAD_CERT = "/updateLoadCert";


    /**
     * 物业获取驻点地址信息
     */
    public static final String GET_PROPERTY_ADDRESS_LIST = "/getPropertyAddressList";


    /**
     * 物业添加驻点地址
     */
    public static final String ADD_PROPERTY_ADDRESS = "/addPropertyAddress";


    /**
     * 请求所有的维保项目
     */
    public static final String GET_ALL_COMMUNITYS_BY_PROPERTY = "/getAllCommunitysByProperty";


    /**
     * 物业公司注册
     */
    public static final String REGISTER_PROPERTY = "/propertyRegist";


    /**
     * 添加物业联系维保公司记录
     */
    public static final String ADD_CONTACT_MAINT = "/addContactMaint";


    /**
     * 维修工获取正在进行中的报修单
     */
    public static final String GET_UNDERWAY_REPAIR = "/getRepairByWorker";


    /**
     * 维修工获取已完成的报修单
     */
    public static final String GET_HISTORY_REPAIR = "/getRepairByWorkerComplete";


    /**
     * 维修工获取已完成的报修单
     */
    public static final String COMMIT_REPAIR_DESCRIBE = "/editRepairByWorker";

    /**
     * 物业获取聊天频道
     */
    public static final String URL_CHAT_CHANNEL = "/getChatChannels";

    /**
     *上传签名
     */
    public static final String URL_UPLOAD_SIGN = "/updateLoadAutograph";

    /**
     * 物业反馈维保不合格
     */
    public static final String URL_MAINT_FAILED = "/backMaint";

    /**
     * 获取维保服务
     */
    public static final String URL_MAINT_SERVICE = "/getMaintOrderByWorker";

    /**
     * 获取指定服务的任务单
     */
    public static final String URL_MAINT_TASK = "/getMaintOrderProcessByMaintOrder";


    /**
     * 维保任务出发
     */
    public static final String URL_MAINT_TASK_START = "/editMaintOrderProcessWorkerSetOut";

    /**
     * 维保任务到达
     */
    public static final String URL_MAINT_TASK_ARRIVE = "/editMaintOrderProcessWorkerArrive";

    /**
     * 维保任务完成
     */
    public static final String URL_MAINT_TASK_FINISH = "/editMaintOrderProcessWorkerFinish";

    /**
     * 任务无法完成
     */
    public static final String URL_MAINT_TASK_UNFINISH = "/editMaintOrderProcessWorkerUnableFinish";


    /**
     * 制定维保计划
     */
    public static final String URL_MAINT_SERVICE_ADD = "/addMaintOrderProcess";

    /**
     * 添加维修任务单
     */
    public static final String URL_TASK_ADD = "/addRepairOrderProcess";

    /**
     * 添加维修支付详情
     */
    public static final String URL_FIX_PAY_ADD = "/addPriceDetails";

    /**
     * 维修工完成
     */
    public static final String URL_FIX_FINISH = "/editRepairOrderProcessWorkerFinish";
    /**
     * 维修工到达
     */
    public static final String URL_FIX_ARRIVE = "/editRepairOrderProcessWorkerArrive";
    /**
     * 维修工出发
     */
    public static final String URL_FIX_START = "/editRepairOrderProcessWorkerSetOut";
    /**
     *根据服务单获取维修任务单
     */
    public static final String URL_FIX_TASK = "/getRepairOrderProcessByRepairOrder";
    /**
     * 根据部门ID获取部门及下级部门信息
     */
    public static final String URL_DEP_LIST = "/getBranchsByBranchId";
    /**
     * 根据部门Id查找维修工
     */
    public static final String URL_WORKER_LIST = "/getWorkListByBranchId";
    /**
     * 维修工获取维修服务单
     */
    public static final String URL_MAINT_LIST = "/getMaintListByBranchId";
    /**
     * 维修工获取维修服务单
     */
    public static final String URL_MAINT_LIST_ELE = "/getMaintListByElevatorId";
    /**
     * 维修工获取维修服务单
     */
    public static final String URL_ALARM_LIST_BRANCH = "/getAlarmListByBranchId";
    /**
     * 维修工获取维修服务单
     */
    public static final String URL_COMPANY_NHMENTENANCE = "/getMaintOrderProcessByBranchIdOnState";
    /**
     * 维修工获取维修服务单
     */
    public static final String URL_COMPANY_NHMENTENANCE_LIST = "/getPaymentByBranchId";
//    /**
//     * 维修工获取维修服务单
//     */
    public static final String URL_FIX_LIST = "/getRepairOrderByWorker";



    /**
     * 维修工获取维修服务单
     */
    public static final String URL_COMPANY_FIX = "/getRepairOrderProcessByBranchIdOnState";

    /**
     * 维修工获取维修服务单
     */
    public static final String URL_COMPANY_FIX_LIST = "/getRepairOrderByBranchId";


    /**
     * 添加聊天记录
     */
    public static final String UP_LOAD_IMG = "/uploadImg";

    /**
     * 添加聊天记录
     */
    public static final String GET_ADVERTISEMENT_DETAIL = "/getAdvertisementDetail";
    /**
     * 添加聊天记录
     */
    public static final String GET_PROCESS_BY_ALARMID = "/getProcessByAlarmId";





    //维保服务标识位
    //维修工任务  添加  标识
    public static final String ADD_STATE = "-1";
    //维修工任务  待确认  标识
    public static final String UNENSURE_STATE = "0";
    //维修工任务  已确认  标识
    public static final String ENSURED_STATE = "1";
    //维修工任务  已出发  标识
    public static final String START_STATE = "2";
    //维修工任务  已到达  标识
    public static final String ARRIVE_STATE = "3";
    //维修工任务  已完成 标识
    public static final String FINISH_STATE = "4";
    //维修工任务  已评价 标识
    public static final String EVA_STATE = "5";
    //维修工任务  已评价 标识
    public static final String UN_FINISH = "6";

    //维修工任务  已评价 标识
    public static final int PAGE = 1;
    //维修工任务  已评价 标识
    public static final int ROWS = 100;

    //end





    //维修任务状态
    //维修工任务  添加  标识
    public static final String FIX_BEFORE_START = "1";
    //维修工任务  待确认  标识
    public static final String FIX_STARTED = "2";
    //维修工任务  已确认  标识
    public static final String FIX_ARRIVED = "3";
    //维修工任务  检修完成  标识
    public static final String FIX_LOOK_FINISHED = "5";
    //维修工任务  已到达  标识
    public static final String FIX_FIX_FINISH = "6";
    //end

    //维修任务结束状态
    // 5 为 检修结束 ，6维修结束
    public static final String FIX_LOOK = "5";
    //维修工任务  待确认  标识
    public static final String FIX_FINISH = "6";
    //end
// 支付详情生成，用于判断是否可以完成维修
    public static final String FIX_PAYMENT_END = "7";

//
    //获取签到状态
    public static final String GET_ATTENDANCE_MAINTENANCE="/getAttendanceMaintenanceByUserId";

    //签到
    public static final String ADD_ATTENDANCE_MAINTENANCE = "/addAttendanceMaintenance";
    @Nullable
    public static final String GETSELECT = "/workOrder/selectAssistant";
    @Nullable
    public static final String GETWORKORDERSBYID = "/workOrder/getWorkOrdersById";
    @Nullable
    public static final String GETWORKORDERMAINTAINITEMBYWORKORDERID="/workOrder/getWorkOrderMaintainItemByWorkOrderId";
    @Nullable
    public static final String GETBAOXIUORMAINTENANCEBYBIZID= "/workOrder/getBaoXiuOrMaintenanceByBizId";
    @Nullable
    public static final String GETWORKORDERBYBIZIDORBIZTYPE = "/workOrder/getWorkOrderByBizIdOrBizType";
    @Nullable
    public static final String GETDRAFTMAINTENANCEDEALLIST = "/getDraftMaintenanceDealList";
    @Nullable
    public static final String GETDEPARTMENT = "/comm/getDepartment";
    @Nullable
    public static final String GETCOMMUNITY = "/comm/getCommunity";
    @Nullable
    public static final String GETSTATUS = "/comm/getStatus";

    @Nullable
    public static final String GETWORKORDERCOUNTBYCOMMUNITY = "/workOrder/getWorkOrderCountByCommunity";
    @NotNull
    public static final String GETDRAFTMAINTENANCEDEALINFO = "/getDraftMaintenanceDealInfo";
    @Nullable
    public static final Object DELETEMAINTENANCEDEALINFO = "/deleteMaintenanceDealInfo";
}
