package com.abdelrahman.core.mapper

import java.util.ArrayList

/**
 * @Author : Abdel-Rahman El-Shikh
 * @Date : 10-Apr-21
 * @Project : com.abdelrahman.core.mapper
 */
abstract class Mapper<INPUT, OUTPUT>  {
    abstract fun map(input: INPUT): OUTPUT
    fun map(inList: List<INPUT>): List<OUTPUT> {
        val outList: ArrayList<OUTPUT> = ArrayList()
        for (input in inList)
            outList.add(map(input))
        return outList
    }
}