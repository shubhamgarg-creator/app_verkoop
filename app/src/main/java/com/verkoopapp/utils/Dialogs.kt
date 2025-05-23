package com.verkoopapp.utils

import android.content.Context

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.*

import com.verkoopapp.R
import kotlinx.android.synthetic.main.delete_comment_dialog.*
import kotlinx.android.synthetic.main.dialog_item_added.*
import kotlinx.android.synthetic.main.dialog_create_offer.*
import kotlinx.android.synthetic.main.dialog_select_met_up.*
import kotlinx.android.synthetic.main.dialog_update_country.*
import kotlinx.android.synthetic.main.proceed_dialog.*
import kotlinx.android.synthetic.main.purchase_coin_dialog.*
import kotlinx.android.synthetic.main.rating_dialog.*
import kotlinx.android.synthetic.main.select_option_dialoog.*
import kotlinx.android.synthetic.main.signup_activity.*
import kotlinx.android.synthetic.main.signup_activity.ccp
import kotlinx.android.synthetic.main.warning_dialog.*


interface SharePostListener{
    fun onWhatAppClick()
    fun onFacebookClick()
    fun onShareClick()
    fun finishDialog()
}
interface SelectionListener{
    fun leaveClick()
}
interface SelectionOptionListener{
    fun leaveClick(option:String)
}
interface MakeOfferListener{
    fun makeOfferClick(offerPrice:Double)
}

interface CountryListener {
    fun onItemClick(code: String, name: String)
}

interface RateUserListener{
    fun rateUserClick(rating:Float,type:String)
}

class ShareProductDialog(context: Context, private val header:String, private val categoryType: String, private val price: String, private val listener:SharePostListener)
    :android.app.Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_item_added)
        tvHeaderDialog.text=header
        tvCategoryDialog.text=categoryType
        tvPriceDialog.text=price
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        setCancelable(false)

        ivFinishDialog.setOnClickListener {
            ivFinishDialog.isEnabled=false
            listener.finishDialog()
            dismiss()
        }
        ivWhatAppShareDialog.setOnClickListener {
            ivWhatAppShareDialog.isEnabled=false
            listener.onWhatAppClick()
            Handler().postDelayed(Runnable {
                ivWhatAppShareDialog.isEnabled = true
            }, 1000)
        }
        tvFacebookShareDialog.setOnClickListener {
            tvFacebookShareDialog.isEnabled=false
            listener.onFacebookClick()
            Handler().postDelayed(Runnable {
                tvFacebookShareDialog.isEnabled = true
            }, 1000)
//            dismiss()
        }
        tvShareDialog.setOnClickListener {
            tvShareDialog.isEnabled=false
            listener.onShareClick()
            Handler().postDelayed(Runnable {
                tvShareDialog.isEnabled = true
            }, 1000)
//            dismiss()
        }
    }
}

class ResumeLocationDialog(context: Context, private val listener:SelectionListener)
    :android.app.Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_select_met_up)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        setCancelable(false)

        tvResume.setOnClickListener {
            dismiss()
        }
        tvLeave.setOnClickListener {
            listener.leaveClick()
            dismiss()
        }
    }
}

class SelectOptionDialog(context: Context, private val listener:SelectionOptionListener)
    :android.app.Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.select_option_dialoog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        setCancelable(false)

        llCamera.setOnClickListener {
            listener.leaveClick(context.getString(R.string.camera))
            dismiss()
        }
        tvGallery.setOnClickListener {
            listener.leaveClick(context.getString(R.string.gallery))
            dismiss()
        }
        llCancel.setOnClickListener {
            dismiss()
        }
    }
}

class DeleteCommentDialog(context: Context,private val header:String,private val description:String, private val listener:SelectionListener)
    :android.app.Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.delete_comment_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        if(header.equals(context.getString(R.string.confirm_accpt_offer),ignoreCase = true)||header.equals(context.getString(R.string.cancel_offer),ignoreCase = true)){
            flParentDel.background=ContextCompat.getDrawable(context,R.drawable.white_rectangular_shape)
        }
        tvHeaderDel.text=header
        tvDescriptionDel.text=description
        tvLeaveDelete.setOnClickListener {
            listener.leaveClick()
            dismiss()
        }
        tvNo.setOnClickListener {
            dismiss()
        }
    }
}

class CreatOfferDialog(private val productType:Int,private val minPrice:Double,private val type:Int,private val offeredPrice:Double,private val realPrice:Double, context: Context, private val listener:MakeOfferListener)
    :android.app.Dialog(context){
    var isFocus:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_create_offer)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        tvSymbol.text = Utils.getPreferencesString(context, AppConstants.CURRENCY_SYMBOL)
        if(type==1){
        etTotalPrice.setText(offeredPrice.toString())
        }else{
        etTotalPrice.setText(realPrice.toString())
        }
        etTotalPrice.setSelection(etTotalPrice.text!!.length)
        llMakeOffer.setOnClickListener {
            listener.makeOfferClick((etTotalPrice.text.toString()).toDouble())
            dismiss()
        }

    }

    fun showDialog(type: Int,context:Context) {
        if(type==1){
            llMakeOffer.visibility=View.GONE
        }else{
            llMakeOffer.visibility=View.VISIBLE
            makeCalculation(context)
        }
    }

    private fun makeCalculation(context:Context) {
        val enteredPrice=etTotalPrice.text.toString()
      if(productType==1){
          if (!TextUtils.isEmpty(enteredPrice) && enteredPrice.toDouble() >= minPrice) {
              llMakeOffer.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
              llMakeOffer.isEnabled = true
          } else {
              Utils.showSimpleMessage(context, "A bid should be higher then or equal to ${Utils.getPreferencesString(context,AppConstants.CURRENCY_SYMBOL)} " + String.format("%.2f", minPrice)).show()
              llMakeOffer.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
              llMakeOffer.isEnabled = false
          }
      }else {
          val sixtyPercent = realPrice * .6
          if (!TextUtils.isEmpty(enteredPrice) && enteredPrice.toDouble() >= sixtyPercent) {
              llMakeOffer.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
              llMakeOffer.isEnabled = true
          } else {
              Utils.showSimpleMessage(context, "A bid should be higher then ${Utils.getPreferencesString(context,AppConstants.CURRENCY_SYMBOL)} " + String.format("%.2f", sixtyPercent)).show()
              llMakeOffer.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
              llMakeOffer.isEnabled = false
          }
      }
    }

}

class countryDialog(context: Context, private val listener: CountryListener)
    : android.app.Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_update_country)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        ccp.setCountryForPhoneCode(1)
        tvSave.setOnClickListener {
            listener.onItemClick(ccp.selectedCountryName, ccp.selectedCountryNameCode)
            dismiss()
        }

    }
}

class WarningDialog(context: Context, private val listener:SelectionListener)
    :android.app.Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.warning_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        setCancelable(false)
        tvProceed.setOnClickListener {
            dismiss()
            listener.leaveClick()
        }
    }
}

class RatingBarDialog(context: Context, private val typeUser: String, private val listener: RateUserListener)
    : android.app.Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.rating_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        setCancelable(false)

        if(typeUser.equals(context.getString(R.string.buyer),ignoreCase = true)){
            tvHeading.text=context.getString(R.string.rate_this_buyer)
        }else{
            tvHeading.text=context.getString(R.string.rate_this_seller)
        }
        tvCancelRate.setOnClickListener {
            dismiss()
        }
        tvSubmitRate.setOnClickListener {
            listener.rateUserClick(rbRating.rating,typeUser)
            dismiss()
        }
    }
}

class PurchaseCoinDialog(context: Context, private val header: StringBuffer, private val listener:SelectionListener)
    :android.app.Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.purchase_coin_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        tvMessage.text=header
        tvYesPur.setOnClickListener {
            listener.leaveClick()
            dismiss()
        }
        tvNoPur.setOnClickListener {
            dismiss()
        }
    }
}

class ProceedDialog(context: Context,private val description:String, private val listener:SelectionListener)
    :android.app.Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.proceed_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        tvProceedPro.setOnClickListener {
            listener.leaveClick()
            dismiss()
        }
        tvCancelPro.setOnClickListener {
            dismiss()
        }
    }
}