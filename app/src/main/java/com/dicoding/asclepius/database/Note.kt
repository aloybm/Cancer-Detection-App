package com.dicoding.asclepius.database

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Note")
@TypeConverters(UriTypeConverter::class)
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "date")
    var date: String? = null,
    @ColumnInfo(name = "imageUri")
    var imageUri: Uri? = null,
    @ColumnInfo(name = "textResult")
    var textResult: String? = null,
) : Parcelable

class UriTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}