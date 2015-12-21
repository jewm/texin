package com.itelg.texin.domain;

import org.junit.Assert;
import org.junit.Test;

public class RowTest
{
	@Test
	public void testConstructor()
	{
		Row row = new Row(1);
		Assert.assertEquals(1, row.getNumber(), 0);
	}
	
	@Test
	public void testToString()
	{
		Assert.assertTrue(new Row(1).toString().startsWith("Row"));
	}
}