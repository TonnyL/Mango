package io.github.tonnyl.mango.util

/*******************************************************************************
 * Copyright (c) 2011 GitHub Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 * Kevin Sawicki (GitHub Inc.) - initial API and implementation
 */

import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/8/10.
 *
 * Parse links from executed method
 */

class PageLinks(response: Response<*>) {

    var next: String? = null
        private set

    var prev: String? = null
        private set

    companion object {

        private val DELIM_LINKS = "," //$NON-NLS-1$

        private val DELIM_LINK_PARAM = ";" //$NON-NLS-1$

        private val HEAD_LINK = "Link"

        private val META_REL = "rel" //$NON-NLS-1$
        private val META_NEXT = "next" //$NON-NLS-1$
        private val META_PREV = "prev" //$NON-NLS-1$
    }

    init {
        val linkHeader = response.headers().get(HEAD_LINK)
        if (linkHeader != null) {
            val links = linkHeader.split(DELIM_LINKS)
            for (link in links) {
                val segments = link.split(DELIM_LINK_PARAM)
                if (segments.size < 2) {
                    continue
                }

                var linkPart = segments[0].trim()
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) { //$NON-NLS-1$ //$NON-NLS-2$
                    continue
                }
                linkPart = linkPart.substring(1, linkPart.length - 1)

                for (i in 1..segments.size - 1) {
                    val rel = segments[i].trim { it <= ' ' }.split("=") //$NON-NLS-1$
                    if (rel.size < 2 || META_REL != rel[0]) {
                        continue
                    }

                    var relValue = rel[1]
                    if (relValue.startsWith("\"") && relValue.endsWith("\"")) { //$NON-NLS-1$ //$NON-NLS-2$
                        relValue = relValue.substring(1, relValue.length - 1)
                    }

                    if (META_NEXT == relValue) {
                        next = linkPart
                    } else if (META_PREV == relValue) {
                        prev = linkPart
                    }
                }
            }
        }
    }

}
