package com.smashrating.match.event;


import com.smashrating.common.exception.CommonErrorCode;
import com.smashrating.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class PendingMatchRequestEvent {
    private final String receiverUsername;
    private final String senderNickname;
    private final String senderRealName;

    public static PendingMatchRequestEvent of(String receiverUsername, String senderNickname, String senderRealName) {
        if(receiverUsername == null || receiverUsername.isBlank()) {
            throw new CustomException(CommonErrorCode.INVALID_PARAMETER, "Receiver username cannot be null or empty");
        }
        if (senderNickname == null || senderNickname.isBlank()) {
            throw new CustomException(CommonErrorCode.INVALID_PARAMETER, "Sender nickname cannot be null or empty");
        }
        if (senderRealName == null || senderRealName.isBlank()) {
            throw new CustomException(CommonErrorCode.INVALID_PARAMETER, "Sender real name cannot be null or empty");
        }
        return new PendingMatchRequestEvent(receiverUsername, senderNickname, senderRealName);
    }
}
