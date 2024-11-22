package com.example.btl_oop.service;

import java.util.List;
import com.example.btl_oop.model.dto.CommentDto;
import com.example.btl_oop.model.request.comment.AddCommentRequest;
import com.example.btl_oop.model.response.comment.AddCommentResponse;

public interface CommentService {

    List<CommentDto> getAllCommentsByRoom_id(long room_id);

    AddCommentResponse addComment (AddCommentRequest request);
}
