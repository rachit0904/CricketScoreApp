package com.cricketexchange.project.Models;

public class CommentaryModal {
    String ballType,commentaryText;

    public CommentaryModal(String ballType, String commentaryText) {
        this.ballType = ballType;
        this.commentaryText = commentaryText;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public String getCommentaryText() {
        return commentaryText;
    }

    public void setCommentaryText(String commentaryText) {
        this.commentaryText = commentaryText;
    }
}
