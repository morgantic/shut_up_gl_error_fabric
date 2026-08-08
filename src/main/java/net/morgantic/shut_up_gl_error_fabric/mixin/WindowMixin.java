package net.morgantic.shut_up_gl_error_fabric.mixin;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Window.class)
public class WindowMixin {
	@Unique
	private final List<Integer> shut_up_gl_error$loggedErrorCodes = new ArrayList<>();

	@Inject(
			method = "defaultErrorCallback",
			at = @At("HEAD"),
			cancellable = true
	)
	private void shut_up_gl_error$interceptGlErrorLogging(int error, long description, CallbackInfo ci) {
		if (this.shut_up_gl_error$loggedErrorCodes.contains(error)) {
			ci.cancel();
		} else {
			this.shut_up_gl_error$loggedErrorCodes.add(error);
		}
	}
}
