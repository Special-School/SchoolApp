package com.specialschool.schoolapp.data.userevent

import com.google.firebase.firestore.DocumentSnapshot
import com.specialschool.schoolapp.model.UserEvent

/**
 * Document snapshot으로 부터 [UserEvent]를 반환한다.
 */
fun parseUserEvent(snapshot: DocumentSnapshot): UserEvent {
    return UserEvent(
        id = snapshot.id,
        isStarred = snapshot["isStarred"] as? Boolean ?: false
    )
}
