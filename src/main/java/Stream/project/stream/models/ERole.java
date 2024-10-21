package Stream.project.stream.models;


public enum ERole {
    USER_ROLE,
    SELLER_ROLE,
    ADMIN_ROLE
;
    public static ERole fromCode(final String code){
      return switch (code) {
        case "ADMIN_ROLE" -> ADMIN_ROLE;
        case "SELLER_ROLE" -> SELLER_ROLE;
        default -> USER_ROLE;
      };
    }
}
