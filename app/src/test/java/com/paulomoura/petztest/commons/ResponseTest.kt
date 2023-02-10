package com.paulomoura.petztest.commons

import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ResponseTest {

    @Test
    fun `Test Response Success behavior`() {
        val success: Response<Long> = Response.Success(10L)
        assert(success is Response.Success)
        assertNotNull(success.data)
        assert(success.data is Long)
        assertNull(success.error)
    }

    @Test
    fun `Test Response Loading behavior`() {
        val loading: Response<Long> = Response.Loading()
        assert(loading is Response.Loading)
        assertNull(loading.data)
        assertNull(loading.error)
    }

    @Test
    fun `Test Response Error behavior with data`() {
        val error: Response<Long> = Response.Error(10L, Exception())
        assert(error is Response.Error)
        assertNotNull(error.data)
        assert(error.data is Long)
        assertNotNull(error.error)
        assert(error.error is Throwable)
    }

    @Test
    fun `Test Response Error behavior without data`() {
        val errorWithNullData: Response<Long> = Response.Error(error = Exception())
        assert(errorWithNullData is Response.Error)
        assertNull(errorWithNullData.data)
        assertNotNull(errorWithNullData.error)
        assert(errorWithNullData.error is Throwable)
    }
}