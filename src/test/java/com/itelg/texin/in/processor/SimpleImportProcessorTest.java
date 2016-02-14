package com.itelg.texin.in.processor;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.itelg.texin.domain.Row;
import com.itelg.texin.domain.exception.NoParserAppliedException;
import com.itelg.texin.domain.exception.ParsingFailedException;
import com.itelg.texin.in.parser.CsvFileParser;

public class SimpleImportProcessorTest
{
	private class ImportProcessor extends SimpleImportProcessor<TestObject>
	{
		@Override
		public void mapRow(Row row)
		{
			TestObject testObject = new TestObject();
			testObject.setColumn1(row.getCells().iterator().next().getStringValue());
			testObject.setRowId(row.getNumber());
			addItem(testObject);
		}
	}

	private class TestObject
	{
		private String column1;
		private int rowId;

		public String getColumn1()
		{
			return column1;
		}

		public void setColumn1(final String column1)
		{
			this.column1 = column1;
		}

		public int getRowId()
		{
			return rowId;
		}

		public void setRowId(int rowId)
		{
			this.rowId = rowId;
		}
	}

	@Test
	public void testValidProcesser() throws ParsingFailedException, NoParserAppliedException, IOException
	{
		try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("testfile.csv"))
		{
			ImportProcessor processor = new ImportProcessor();
			processor.parse("testfile.csv", stream);
			Assert.assertEquals(2, processor.getItems().size());

			for (TestObject testObject : processor.getItems())
			{
				if (testObject.getColumn1().equals("string1"))
				{
					Assert.assertEquals(1, testObject.getRowId(), 0);
				}
				else if (testObject.getColumn1().equals("string2"))
				{
					Assert.assertEquals(2, testObject.getRowId(), 0);
				}
				else
				{
					Assert.fail();
				}

				System.out.println("TestObject: " + testObject.getColumn1());
			}
		}
	}

	@Test
	public void testConfiguredFileParser() throws ParsingFailedException, NoParserAppliedException, IOException
	{
		try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("testfile2.csv"))
		{
			ImportProcessor processor = new ImportProcessor();

			CsvFileParser fileParser = new CsvFileParser();
			fileParser.setCellDelimeter("-");
			processor.addFileParser(fileParser);

			processor.parse("testfile2.csv", stream);
			Assert.assertEquals(1, processor.getItems().size());
		}
	}

	@Test(expected = NoParserAppliedException.class)
	public void testNoParsedAppliedException() throws ParsingFailedException, NoParserAppliedException, IOException
	{
		try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("testfile.csv"))
		{
			ImportProcessor processor = new ImportProcessor();
			processor.parse("testfile.pdf", stream);
		}
	}
}