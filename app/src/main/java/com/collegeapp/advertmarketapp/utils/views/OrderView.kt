package com.collegeapp.advertmarketapp.utils.views

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.AppDatabase
import com.collegeapp.advertmarketapp.database.dao.ClientDAO
import com.collegeapp.advertmarketapp.database.dao.OrderDAO
import com.collegeapp.advertmarketapp.database.entity.Order
import com.collegeapp.advertmarketapp.utils.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class OrderView(context: Context, val order: Order, attrs: AttributeSet?) : LinearLayout(
        context,
        attrs
) {

    private var db: AppDatabase? = null
    private var clientDAO: ClientDAO? = null
    private var orderDAO: OrderDAO? = null

    private val title: TextView
    private val id: TextView
    private val clientName: TextView
    private val clientEmail: TextView
    private val clientPhone: TextView
    private val orderArea: TextView
    private val orderClient: TextView

    private val chooseColor: Button
    private val colorPreview: CardView
    private val colorHex: TextView
    private val tzBtn: Button
    private val tzTxt: TextView

    private val planMaterialTxt: EditText
    private val planMaterialLbl: TextView

    private val divider3: View
    private val stage1: View
    private val divider4: View
    private val stage2: View
    private val divider5: View
    private val stage3: View
    private val divider6: View
    private val stage4: View

    private val planWork: EditText
    private val planWorkLbl: TextView
    private val loadRes: Button
    private val uriRes: TextView

    private val orderSq: EditText
    private val orderPrice: EditText
    private val orderSqTxt: TextView
    private val orderPriceTxt: TextView

    private val comment: EditText
    private val commentTxt: TextView

    private val saveOrder: Button

    private var tzURL: Uri? = null
    private var resURL: Uri? = null

    init {
        val root = inflate(context, R.layout.content_order, this)

        title = root.findViewById(R.id.title_order_txt)
        id = root.findViewById(R.id.order_id_txt)
        clientName = root.findViewById(R.id.client_name_txt)
        clientEmail = root.findViewById(R.id.client_email_txt)
        clientPhone = root.findViewById(R.id.client_phone_txt)
        orderArea = root.findViewById(R.id.order_area_txt)
        orderClient = root.findViewById(R.id.order_client_id)

        chooseColor = root.findViewById(R.id.choose_color)
        colorPreview = root.findViewById(R.id.color_preview)
        colorHex = root.findViewById(R.id.color_hex)

        tzBtn = root.findViewById(R.id.tz_btn)
        tzTxt = root.findViewById(R.id.url_tz_txt)

        planMaterialTxt = root.findViewById(R.id.plan_material_txt)
        planMaterialLbl = root.findViewById(R.id.plan_material_lbl)

        divider3 = root.findViewById(R.id.divider3)
        stage1 = root.findViewById(R.id.stage_1)

        divider4 = root.findViewById(R.id.divider4)
        stage2 = root.findViewById(R.id.stage_2)

        divider5 = root.findViewById(R.id.divider5)
        stage3 = root.findViewById(R.id.stage_3)

        divider6 = root.findViewById(R.id.divider6)
        stage4 = root.findViewById(R.id.stage_4)

        planWork = root.findViewById(R.id.plan_work)
        planWorkLbl = root.findViewById(R.id.plan_work_lbl)
        loadRes = root.findViewById(R.id.load_res)
        uriRes = root.findViewById(R.id.res_url)

        orderSq = root.findViewById(R.id.order_sq)
        orderPrice = root.findViewById(R.id.order_price)
        orderSqTxt = root.findViewById(R.id.square_txt)
        orderPriceTxt = root.findViewById(R.id.price_order_lbl)

        comment = root.findViewById(R.id.comment_order)
        commentTxt = root.findViewById(R.id.comment_txt)

        saveOrder = root.findViewById(R.id.save_order)

        if (!isInEditMode) {
            db = app.getDatabase()
            clientDAO = db?.clientDAO()
            orderDAO = db?.orderDAO()

            setOrder()

            chooseColor.setOnClickListener {
                chooseColor()
            }

            tzBtn.setOnClickListener {
                loadFile(CHOOSE_FILE, "text/plain")
            }

            saveOrder.setOnClickListener {
                save()
            }

            loadRes.setOnClickListener {
                loadFile(CHOOSE_FILE_RES, "image/*")
            }

            when (order.status) {
                StatusCode.CLIENT -> {
                    if (app.currentUser?.role == ProfessionCode.DIRECTOR) {
                        stage1.visibility = GONE
                        divider3.visibility = GONE

                        saveOrder.visibility = GONE
                    }
                }
                StatusCode.CREATION -> {
                    status2()
                    if (app.currentUser?.role == ProfessionCode.DIRECTOR) {
                        stage2.visibility = GONE
                        divider4.visibility = GONE

                        saveOrder.visibility = GONE
                    }
                }
                StatusCode.PRODUCTION -> {
                    status3()
                    if (app.currentUser?.role == ProfessionCode.DIRECTOR) {
                        stage3.visibility = GONE
                        divider5.visibility = GONE

                        saveOrder.visibility = GONE
                    }
                }
                StatusCode.MEDIA -> {
                    status4()
                }
                StatusCode.REPORT -> {
                    status5()
                }
            }
        }
    }

    private fun setOrder() {
        title.text = order.title
        id.text = "#${order.id}"
        orderArea.text = order.areaDescription
        orderClient.text = order.clientDescription

        CoroutineScope(Dispatchers.Main).launch {
            val client = clientDAO?.getById(order.clientId.toInt())

            withContext(Dispatchers.Main) {
                clientName.text = client?.name
                clientEmail.text = client?.email
                clientPhone.text = client?.phone
            }
        }
    }

    private fun chooseColor() {
        ColorPickerDialogBuilder
                .with(context)
                .setTitle("Выберите цвет")
                .initialColor(R.color.background)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener { selectedColor ->
                    println("onColorSelected: 0x" + Integer.toHexString(selectedColor))
                }
                .setPositiveButton("OK") { _, selectedColor, _ ->
                    val colorString = "#${Integer.toHexString(selectedColor)}"
                    val color = Color.parseColor(colorString)

                    colorPreview.setCardBackgroundColor(color)
                    colorHex.text = colorString
                }
                .setNegativeButton("Отмена", null)
                .build()
                .show()
    }

    private fun loadFile(code: Int, type: String) {
        val intent: Intent
        val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
        chooseFile.type = type
        intent = Intent.createChooser(chooseFile, "Choose a file")

        startActivityForResult(activity!!, intent, code, null)
    }

    fun setUrl(url: Uri?, code: Int) {
        when (code) {
            CHOOSE_FILE -> {
                tzURL = url
                tzTxt.text = url?.path?.split("/")?.last()
            }
            CHOOSE_FILE_RES -> {
                resURL = url
                uriRes.text = url?.path?.split("/")?.last()
            }
        }
    }

    private fun save() {
        when (order.status) {
            StatusCode.CLIENT -> {
                order.color = colorHex.text.toString()
                order.plan = planMaterialTxt.text.toString()


                order.urlTZ = tzURL?.lastPathSegment
            }

            StatusCode.CREATION -> {
                order.planWork = planWork.text.toString()
                order.urlRes = resURL?.lastPathSegment
            }

            StatusCode.PRODUCTION -> {
                order.listSquare = orderSq.text.toString()
                order.price = orderPrice.text.toString().toInt()
            }

            StatusCode.MEDIA -> {
                order.urlReport = comment.text.toString()
            }
        }
        order.status += 1
        CoroutineScope(Dispatchers.Main).launch {
            orderDAO?.update(order)
        }
        findNavController().popBackStack()
    }

    private fun status2() {
        planMaterialLbl.visibility = VISIBLE
        planMaterialLbl.text = order.plan

        chooseColor.visibility = GONE
        planMaterialTxt.visibility = GONE
        tzBtn.visibility = GONE

        colorHex.text = order.color
        tzTxt.text = order.urlTZ

        val color = Color.parseColor(order.color)

        colorPreview.setCardBackgroundColor(color)

        divider4.visibility = VISIBLE
        stage2.visibility = VISIBLE
    }

    private fun status3() {
        status2()
        planWork.visibility = GONE
        planWorkLbl.visibility = VISIBLE
        planWorkLbl.text = order.planWork

        loadRes.visibility = GONE
        uriRes.text = order.urlRes

        divider5.visibility = VISIBLE
        stage3.visibility = VISIBLE
    }

    private fun status4() {
        status3()

        orderSq.visibility = GONE
        orderPrice.visibility = GONE

        orderSqTxt.visibility = VISIBLE
        orderPriceTxt.visibility = VISIBLE

        orderSqTxt.text = order.listSquare
        orderPriceTxt.text = "${order.price.toString()}, р."

        divider6.visibility = VISIBLE
        stage4.visibility = VISIBLE
    }

    private fun status5() {
        status4()

        comment.visibility = GONE
        commentTxt.visibility = VISIBLE

        commentTxt.text = order.urlReport

        saveOrder.visibility = GONE
    }

}