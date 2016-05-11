// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.decentApp;

import java.util.*;
import java.io.*;

/**
 * Helper class for watching if a file was changed
 * 
 * @author Philip Makedonski
 * 
 */
public abstract class FileWatcher extends TimerTask {
    // Last timestamp
    private long timeStamp;

    // File to watch
    private File file;

    /**
     * Constructor
     * 
     * @param file
     */
    public FileWatcher(File file) {
        this.file = file;
        this.timeStamp = file.lastModified();
    }

    /**
     * Watches a file and executes the onChange Method if a file is changed
     */
    public final void run() {
        long timeStamp = file.lastModified();

        if (this.timeStamp != timeStamp) {
            this.timeStamp = timeStamp;
            onChange(file);
        }
    }

    protected abstract void onChange(File file);
}
