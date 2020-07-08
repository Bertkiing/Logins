package com.bert.googlegame

import android.content.Context
import android.widget.Toast

/**
 *
 * @Author: bertking
 * @ProjectName: Googles
 * @CreateAt: 2020-07-07 10:46
 * @UpdateAt: 2020-07-07 10:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description:
 */

object Utils{

    fun showToast(context: Context,string: String){
       Toast.makeText(context,string,Toast.LENGTH_LONG).show()
    }
}