/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datareporsitories;

/**
 *
 * @author ASOS
 */
public class mediaModel {
     String userId = "";
     String displayUrl = "";
     String edgeLikedByCount = "";
     String edgeMediaToCommentCount = "";
     String edgeMediaToCaptionText = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getEdgeLikedByCount() {
        return edgeLikedByCount;
    }

    public void setEdgeLikedByCount(String edgeLikedByCount) {
        this.edgeLikedByCount = edgeLikedByCount;
    }

    public String getEdgeMediaToCommentCount() {
        return edgeMediaToCommentCount;
    }

    public void setEdgeMediaToCommentCount(String edgeMediaToCommentCount) {
        this.edgeMediaToCommentCount = edgeMediaToCommentCount;
    }

    public String getEdgeMediaToCaptionText() {
        return edgeMediaToCaptionText;
    }

    public void setEdgeMediaToCaptionText(String edgeMediaToCaptionText) {
        this.edgeMediaToCaptionText = edgeMediaToCaptionText;
    }
     
     
     
}
