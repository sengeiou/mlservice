package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.ml_tech.mx.mlservice.DAO.P_SourceOperator;
import cn.ml_tech.mx.mlservice.DAO.P_UserTypePermission;

/**
 * 创建时间: 2017/11/2
 * 创建人: Administrator
 * 功能描述:权限工具类
 */

public class PermissionUtil {
    private static PermissionUtil permissionUtil;
    private Context context;

    public enum TYPE {
        ADD, DELETE
    }

    private PermissionUtil(Context context) {
        this.context = context;
    }

    public static PermissionUtil getInstance(Context context) {
        if (permissionUtil == null) {
            permissionUtil = new PermissionUtil(context);
        }
        return permissionUtil;
    }

    /**
     * 检测权限
     *
     * @param operateId  操作类型id
     * @param sourcesId  资源类型id
     * @param userTypeId 用户类型id
     * @return true : rightType = 1 , false rightType = 0 或不存在
     */
    public boolean checkPermission(long operateId, long sourcesId, long userTypeId) {
        boolean res = false;
        List<P_SourceOperator> p_sourceOperators =
                DataSupport.where("p_source_id = ? and p_operator_id =?"
                        , sourcesId + "", operateId + "").find(P_SourceOperator.class);
        if (p_sourceOperators != null && p_sourceOperators.size() != 0) {
            long sourceOperateId = p_sourceOperators.get(0).getId();
            List<P_UserTypePermission> p_userTypePermissions =
                    DataSupport.where("p_sourceoperator_id = ? and usertype = ?",
                            sourceOperateId + "", userTypeId + "").find(P_UserTypePermission.class);
            if (p_userTypePermissions != null && p_userTypePermissions.size() != 0) {
                if (p_userTypePermissions.get(0).getRighttype() == 1) {
                    res = true;
                }
            }
        }
        return res;
    }


    /**
     * 操作权限
     *
     * @param operateId  操作类型Id
     * @param sourcesId  资源类型Id
     * @param userTypeId 用户类型Id
     * @param type       操作类型Id
     */
    public void operatePermission(final long operateId, final long sourcesId, final long userTypeId, final TYPE type) {
        new Thread() {
            public void run() {
                super.run();
                List<P_SourceOperator> p_sourceOperators =
                        DataSupport.where("p_source_id = ? and p_operator_id =?"
                                , sourcesId + "", operateId + "").find(P_SourceOperator.class);
                if (p_sourceOperators != null && p_sourceOperators.size() != 0) {
                    long sourceOperateId = p_sourceOperators.get(0).getId();
                    List<P_UserTypePermission> p_userTypePermissions =
                          DataSupport.where("p_sourceoperator_id = ? and usertype = ?",
                                    sourceOperateId + "", userTypeId + "").find(P_UserTypePermission.class);
                    if (p_userTypePermissions == null && p_userTypePermissions.size() == 0) {
                        if (type == TYPE.ADD) {
                            P_UserTypePermission p_userTypePermission = new P_UserTypePermission();
                            p_userTypePermission.setRighttype(1);
                            p_userTypePermission.setP_sourceoperator_id(sourceOperateId);
                            p_userTypePermission.setUsertype(userTypeId);
                            p_userTypePermission.save();
                        }
                    } else {
                        Log.d("zw", "type " + type.ordinal() + " ss" + TYPE.ADD.ordinal());

                        P_UserTypePermission p_userTypePermission = p_userTypePermissions.get(0);
                        if (type == TYPE.ADD) {
                            Log.d("zw", "add permission");

                            p_userTypePermission.setRighttype(1);
                            p_userTypePermission.save();
                        } else {
                            Log.d("zw", "delete permission");

                            p_userTypePermission.delete();
                        }
                    }
                }
            }
        }.start();
    }

}
