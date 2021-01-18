package com.dowgalolya.gwenthelper

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dowgalolya.gwenthelper.db.GameScoreDatabase
import com.dowgalolya.gwenthelper.di.SingletonHolder
import com.dowgalolya.gwenthelper.enums.Winner
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MigrationTest {
    companion object {
        private const val TEST_DB = "migration-test"
    }

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        GameScoreDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrationFrom1To2() {
        val db = helper.createDatabase(TEST_DB, 1)

        val values = ContentValues().apply {
            put("date", "some date")
            put("first_player", "User 1")
            put("second_player", "User 2")
            put("winner", Winner.FIRST.name)
        }

        db.insert("game_score", SQLiteDatabase.CONFLICT_REPLACE,values)

        helper.runMigrationsAndValidate(TEST_DB,2, true, GameScoreDatabase.MigrationFrom1To2)

    }

}