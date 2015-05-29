package com.neogrid;

import java.io.Serializable;

import org.apache.commons.io.FilenameUtils;

public class ZkFile implements Serializable, Comparable<ZkFile> {

    private static final long serialVersionUID = -8518438924339972681L;

    private static final String SLASH = "/";

    /**
     * This abstract pathname's normalized pathname string. A normalized
     * pathname string uses the default name-separator character and does not
     * contain any duplicate or redundant separators.
     */
    private String path;

    /**
     * The length of this abstract pathname's prefix, or zero if it has no
     * prefix.
     */
    private transient int prefixLength;

    public ZkFile(ZkFile parent, String child) {
        if (child == null) {
            throw new NullPointerException();
        }
        if (parent == null) {
            if (child.startsWith(SLASH)) {
                this.path = child;
            } else {
                this.path = SLASH.concat(child);
            }
        } else {
            if ("".equals(parent)) {
                if (child.startsWith(SLASH)) {
                    this.path = child;
                } else {
                    this.path = SLASH.concat(child);
                }
            } else {
                if (parent.getPath().startsWith(SLASH)) {
                    this.path = parent.getPath().concat(SLASH).concat(child);
                } else {
                    this.path = SLASH.concat(parent.getPath()).concat(SLASH).concat(child);
                }
            }
        }
        this.path = FilenameUtils.normalize(path);
        this.prefixLength = FilenameUtils.getPrefixLength(path);
    }

    public ZkFile(String pathname) {
        if (pathname == null) {
            throw new NullPointerException();
        }
        this.path = FilenameUtils.normalize(pathname);
        this.prefixLength = FilenameUtils.getPrefixLength(path);
    }

    private ZkFile(String parent, int prefixLength) {
        this.path = parent;
        this.prefixLength = prefixLength;
    }

    public String getName() {
        return FilenameUtils.getName(path);
    }

    public ZkFile getParentFile() {
        String parent = this.getParent();
        if (parent == null) {
            return null;
        }
        return new ZkFile(parent, this.prefixLength);
    }

    public String getPath() {
        return FilenameUtils.separatorsToUnix(path);
    }

    /**
     * Compares two abstract pathnames lexicographically.  The ordering
     * defined by this method depends upon the underlying system.  On UNIX
     * systems, alphabetic case is significant in comparing pathnames; on Microsoft Windows
     * systems it is not.
     *
     * @param   pathname  The abstract pathname to be compared to this abstract
     *                    pathname
     * 
     * @return  Zero if the argument is equal to this abstract pathname, a
     *		value less than zero if this abstract pathname is
     *		lexicographically less than the argument, or a value greater
     *		than zero if this abstract pathname is lexicographically
     *		greater than the argument
     *
     * @since   1.2
     */
    public int compareTo(ZkFile path) {
    	return this.getPath().compareTo(path.getPath());
    }

    /**
     * Returns the pathname string of this abstract pathname.  This is just the
     * string returned by the <code>{@link #getPath}</code> method.
     *
     * @return  The string form of this abstract pathname
     */
    public String toString() {
	    return getPath();
    }

    /**
     * Tests this abstract pathname for equality with the given object.
     * Returns <code>true</code> if and only if the argument is not
     * <code>null</code> and is an abstract pathname that denotes the same file
     * or directory as this abstract pathname.  Whether or not two abstract
     * pathnames are equal depends upon the underlying system.  On UNIX
     * systems, alphabetic case is significant in comparing pathnames; on Microsoft Windows
     * systems it is not.
     *
     * @param   obj   The object to be compared with this abstract pathname
     *
     * @return  <code>true</code> if and only if the objects are the same;
     *          <code>false</code> otherwise
     */
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ZkFile) {
	        return compareTo((ZkFile) obj) == 0;
        }
	    return false;
    }

    private String getParent() {
        int index = FilenameUtils.indexOfLastSeparator(path);
        if (index < prefixLength) {
            if (prefixLength > 0 && path.length() > prefixLength) {
                return path.substring(0, prefixLength);
            }
            return null;
        }
        return path.substring(0, index);
    }

}
