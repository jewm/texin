package com.itelg.texin.in.parser;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.itelg.texin.domain.Row;
import com.itelg.texin.domain.exception.ParsingFailedException;

public class CsvFileParserTest
{
	private int parsedLines = 0;

	@Test
	public void testApplies()
	{
		Assert.assertTrue(new CsvFileParser().applies("test.csv"));
		Assert.assertFalse(new CsvFileParser().applies("test.txt"));
		Assert.assertFalse(new CsvFileParser().applies("test.xlsx"));
	}

	@Test
	public void testParse() throws ParsingFailedException, IOException
	{
		try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("testfile.csv"))
		{
			FileParser parser = new CsvFileParser();

			parser.setRowParsedListener(new RowParsedListener()
			{
				@Override
				public void parsed(final Row row)
				{
					parsedLines++;
				}
			});
			parser.parse(stream);
			Assert.assertEquals(2, parsedLines, 0.1);
		}
	}
}