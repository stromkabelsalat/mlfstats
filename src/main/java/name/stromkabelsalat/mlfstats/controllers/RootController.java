package name.stromkabelsalat.mlfstats.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import name.stromkabelsalat.mlfstats.components.RootComponent;
import name.stromkabelsalat.mlfstats.model.Root;

@Controller
@RequiredArgsConstructor
public class RootController {
	private final RootComponent rootComponent;

	@GetMapping
	public String root(final Model model) {
		final Root rootTO = this.rootComponent.root();
		model.addAttribute(rootTO);

		return "root";
	}
}
