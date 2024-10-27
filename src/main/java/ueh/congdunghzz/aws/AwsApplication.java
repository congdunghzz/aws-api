package ueh.congdunghzz.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ueh.congdunghzz.aws.common.enums.ActiveStatus;
import ueh.congdunghzz.aws.common.enums.UserRole;
import ueh.congdunghzz.aws.common.util.UniqueID;
import ueh.congdunghzz.aws.enitity.Image;
import ueh.congdunghzz.aws.enitity.User;
import ueh.congdunghzz.aws.model.request.RegisterRequest;
import ueh.congdunghzz.aws.repository.ImageRepository;
import ueh.congdunghzz.aws.repository.UserRepository;
import ueh.congdunghzz.aws.service.AuthenticationService.AuthenticationService;
import ueh.congdunghzz.aws.service.image.ImageService;
import ueh.congdunghzz.aws.service.user.UserService;

@SpringBootApplication
@RequiredArgsConstructor
public class AwsApplication {
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;

	public static void main(String[] args) {
		SpringApplication.run(AwsApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			User user = User.builder()
					.id("30babad8a0da4118a4910bfb53634a1b")
					.role(UserRole.USER)
					.status(ActiveStatus.ACTIVE)
					.name("Cong Dung")
					.username("congdunghzz")
					.password(passwordEncoder().encode("123"))
					.build();
			User user2 = User.builder()
					.id("7866fa07e9e5488d891363260d201fa9")
					.role(UserRole.USER)
					.status(ActiveStatus.ACTIVE)
					.name("Bao Duy")
					.username("baoduy")
					.password(passwordEncoder().encode("123"))
					.build();
			userRepository.insert(user);
			userRepository.insert(user2);

			Image image = Image.builder()
					.id("5008b6362f9a42bfb94bcde5963f9cf3")
					.ownerName(user.getName())
					.ownedBy("30babad8a0da4118a4910bfb53634a1b")
					.title("Team building DigiEx")
					.name("PPQ02745.JPG")
					.contentType("image/jpeg")
					.createDate(1730006429575L)
					.url("https://moment-sharing.s3.ap-southeast-1.amazonaws.com/30babad8a0da4118a4910bfb53634a1b/d516346294744ab8b3c85a5ef523d3c0/PPQ02745.JPG")
					.key("30babad8a0da4118a4910bfb53634a1b/d516346294744ab8b3c85a5ef523d3c0/PPQ02745.JPG")
					.build();
			Image image2 = Image.builder()
					.id("f529eba3b0da407f97441eedaf07d5a0")
					.ownerName(user2.getName())
					.ownedBy("7866fa07e9e5488d891363260d201fa9")
					.title("Meows")
					.name("meo-cam-bong-hoa-tren-tay-manh-me-len.jpg")
					.contentType("image/jpeg")
					.createDate(1730006799490L)
					.url("https://moment-sharing.s3.ap-southeast-1.amazonaws.com/7866fa07e9e5488d891363260d201fa9/6e5fedb86c7946fdb54fdba3113f7e32/meo-cam-bong-hoa-tren-tay-manh-me-len.jpg")
					.key("7866fa07e9e5488d891363260d201fa9/6e5fedb86c7946fdb54fdba3113f7e32/meo-cam-bong-hoa-tren-tay-manh-me-len.jpg")
					.build();
			imageRepository.insert(image);
			imageRepository.insert(image2);
        };
    }
}
