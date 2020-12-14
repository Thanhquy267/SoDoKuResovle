package com.quyT.sodokuresovle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.quyT.sodokuresovle.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val listData = ArrayList<ArrayList<Int>>()
    lateinit var binding: ActivityMainBinding
    var adapter: RootAdapter? = null

    //
    lateinit var markRow: Array<BooleanArray>
    lateinit var markCol: Array<BooleanArray>
    lateinit var markMatrix: Array<Array<BooleanArray>>

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //
        Glide.with(this).load(getBoxImage(8, 8)).into(binding.ivTest)
//        for (i in 0..8) {
//            val listInt = ArrayList<Int>()
//            for (j in 0..8) {
//                listInt.add(0)
//            }
//            listData.add(listInt)
//        }
//        adapter = RootAdapter(ArrayList(listData))
//        binding.rvList.adapter = adapter
//        binding.rvList.layoutManager = LinearLayoutManager(this)
//
//
//        binding.btn.setOnClickListener {
//            getData()
//            resolve()
//        }
    }

    private fun getTextFromImage(bitmap : Bitmap,succ) {
        val recognizer = TextRecognition.getClient()
        val image = InputImage.fromBitmap(bitmap, 0)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                return visionText.text
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
    }

    private fun getBoxImage(i: Int, j: Int): Bitmap {
        val image = BitmapFactory.decodeResource(this.resources, R.drawable.test_iamge_2)
        val valueNum = image.width / 9
        val START_X = i * valueNum
        val START_Y = j * valueNum
        val WIDTH_PX = valueNum
        val HEIGHT_PX = valueNum

        val newBitmap =
            Bitmap.createBitmap(image, START_X, START_Y, WIDTH_PX, HEIGHT_PX, null, false)
        return newBitmap
    }

    private fun getData() {
        for (i in 0..8) {
            for (j in 0..8) {
                listData[i][j] = getNum(i, j)
            }
        }
    }

    private fun getNum(i: Int, j: Int): Int {
        val rootViewHolder = binding.rvList.findViewHolderForLayoutPosition(i) as RootViewHolder
        val viewHolder =
            rootViewHolder.binding.rvNum.findViewHolderForLayoutPosition(j) as ViewHolder
        return if (viewHolder.binding.etNum.text.toString().isNotEmpty())
            viewHolder.binding.etNum.text.toString().toInt()
        else
            0
    }

    private fun resolve() {
        markRow = Array(9) { BooleanArray(9) }
        markCol = Array(9) { BooleanArray(9) }
        markMatrix = Array(3) { Array(3) { BooleanArray(9) } }
        for (i in listData.indices) {
            for (j in 0 until listData[i].size) {
                if (listData[i][j] != 0) {
                    val num = listData[i][j]
                    markRow[i][num - 1] = true
                    markCol[j][num - 1] = true
                    markMatrix[i / 3][j / 3][num - 1] = true
                }
            }
        }
        solver(0, 0)
    }

    private fun solver(i: Int, j: Int) {
        if (i < 9 && j < 9) {
            if (listData[i][j] == 0) {
                for (z in 1..9) {
                    if (!markRow[i][z - 1] && !markCol[j][z - 1] && !markMatrix[i / 3][j / 3][z - 1]
                    ) {
                        markRow[i][z - 1] = true
                        markCol[j][z - 1] = true
                        markMatrix[i / 3][j / 3][z - 1] = true
                        listData[i][j] = z
                        solver(i, j + 1)
                        markRow[i][z - 1] = false
                        markCol[j][z - 1] = false
                        markMatrix[i / 3][j / 3][z - 1] = false
                        listData[i][j] = 0
                    }
                }
            } else {
                solver(i, j + 1)
            }
        } else if (i < 9 && j >= 9) {
            solver(i + 1, 0)
        } else {
            handleResult()
        }
    }

    private fun handleResult() {
        Log.d("Matrix", listData.toString())
        adapter?.setData(listData)

    }
}