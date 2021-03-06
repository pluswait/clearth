/******************************************************************************
 * Copyright 2009-2020 Exactpro Systems Limited
 * https://www.exactpro.com
 * Build Software to Test Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.exactprosystems.clearth.automation.schedulerinfo;

import java.io.File;

public class SchedulerInfoFile
{
	private final String outputPath;
	private final File originalFile;
	
	private boolean include = true;
	
	public SchedulerInfoFile(String outputPath, File originalFile)
	{
		this.outputPath = outputPath;
		this.originalFile = originalFile;
	}
	
	public String getOutputPath()
	{
		return outputPath;
	}
	
	public File getOriginalFile()
	{
		return originalFile;
	}
	
	
	public void setInclude(boolean include)
	{
		this.include = include;
	}
	
	public boolean isInclude()
	{
		return include;
	}
}
