package nodrify.inc.octane.iam.infrastructure.hashing.bcrypt;

import nodrify.inc.octane.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {




}
