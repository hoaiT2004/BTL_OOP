package com.example.btl_oop.service.Impl;

import com.example.btl_oop.entity.Comment;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.dto.CommentDto;
import com.example.btl_oop.model.request.comment.AddCommentRequest;
import com.example.btl_oop.model.response.comment.AddCommentResponse;
import com.example.btl_oop.repository.CommentRepository;
import com.example.btl_oop.repository.UserRepository;
import com.example.btl_oop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CommentDto> getAllCommentsByRoom_id(long room_id) {
        List<Comment> comments = commentRepository.getCommentsByRoom_id(room_id);
        return CommentDto.toDto(comments);
    }

    @Override
    public AddCommentResponse addComment(AddCommentRequest request) {
        // Định dạng thời gian theo kiểu "yyyy-MM-dd HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Chuyển đổi commentTime từ String sang LocalDateTime
        LocalDateTime commentTime = LocalDateTime.parse(request.getCommentTime(), formatter);

        User user = userRepository.findUserByUsername(request.getUsername()).orElse(null);

        Comment comment = Comment.builder()
                .username(request.getUsername())
                .avatar(user.getLinkAvatar())
                .room_id(Long.parseLong(request.getRoom_id()))
                .content(request.getContent())
                .commentTime(commentTime)
                .build();
        comment = commentRepository.save(comment);
        return new AddCommentResponse(comment.getId());
    }
}
