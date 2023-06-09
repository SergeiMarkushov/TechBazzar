import React from "react";
import {Accordion, AccordionDetails, AccordionSummary, Typography} from "@mui/material";
import ExpandMore from "@mui/icons-material/ExpandMore";

interface DeveloperDescriptionProps {
	name: string,
	telegramLink: string
}

export function DeveloperDescription({ name, telegramLink }: DeveloperDescriptionProps) {
	return (
		<Accordion>
			<AccordionSummary
				expandIcon={<ExpandMore />}
				aria-controls="panel1a-content"
				id="panel1a-header"
			>
				<Typography>{name}</Typography>
			</AccordionSummary>
			<AccordionDetails>
				<a href={telegramLink}>Telegram</a>
			</AccordionDetails>
		</Accordion>
	)
}