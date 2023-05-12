package com.example.activityandfragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.activityandfrugments.R

const val TAG = "First"

val NAME_COLOR = arrayListOf(
    NameColor(name = "white", color = R.color.white),
    NameColor(name = "Red", color = R.color.red),
    NameColor(name = "Green", color = R.color.green)
)


class MainActivity : AppCompatActivity() {
    private lateinit var buttonContainer: Button

    private val changeColor =
        registerForActivityResult(
            MyContract(
                SecondActivity::class.java,
                NAME_COLOR
            )
        ) { colorName ->
            if (colorName != null) {
                NAME_COLOR.forEach {
                    if (it.name==colorName)buttonContainer.setBackgroundResource(it.color)
                }
                Toast.makeText(
                    this,
                    "Pressed\t${colorName}\tcolor",
                    Toast.LENGTH_LONG
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        buttonContainer = findViewById<Button?>(R.id.button_add).apply {
            this.setOnClickListener {
                changeColor.launch(TAG)
            }
        }
    }
}

class NameColor( val name: String?,  val color: Int) : Parcelable {
    constructor(parcel: Parcel) : this(name = parcel.readString(), color = parcel.readInt()) {

    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(color)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NameColor> {
        override fun createFromParcel(parcel: Parcel): NameColor {
            return NameColor(parcel)
        }
        override fun newArray(size: Int): Array<NameColor?> {
            return arrayOfNulls(size)
        }
    }
}


