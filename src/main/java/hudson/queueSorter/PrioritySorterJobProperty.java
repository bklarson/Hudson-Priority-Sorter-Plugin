/*
 * The MIT License
 *
 * Copyright (c) 2010, Brad Larson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.queueSorter;

import hudson.Extension;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class PrioritySorterJobProperty extends
		JobProperty<AbstractProject<?, ?>> {

	public final int priority;

	@DataBoundConstructor
	public PrioritySorterJobProperty(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension
	public static final class DescriptorImpl extends JobPropertyDescriptor {
		int userBuildPriority;
		int scmPriority;
		int timerPriority;

		public DescriptorImpl() {
			super(PrioritySorterJobProperty.class);
			load();
		}

		@Override
		public String getDisplayName() {
			return "Job Priority";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject json)
				throws hudson.model.Descriptor.FormException {
			try {
				userBuildPriority = Integer.valueOf(json
						.getString("userBuildPriority"));
			} catch (NumberFormatException e) {
				userBuildPriority = 0;
			}
			try {
				scmPriority = Integer.valueOf(json.getString("scmPriority"));
			} catch (NumberFormatException e) {
				scmPriority = 0;
			}
			try {
				timerPriority = Integer
						.valueOf(json.getString("timerPriority"));
			} catch (NumberFormatException e) {
				timerPriority = 0;
			}
			save();
			return super.configure(req, json);
		}

		public int getUserBuildPriority() {
			return userBuildPriority;
		}

		public int getScmPriority() {
			return scmPriority;
		}

		public int getTimerPriority() {
			return timerPriority;
		}
	}
}
