import React from 'react';
import { CircularProgress } from "@mui/material";
import Box from "@mui/material/Box/Box";

export function CircularLoading() {
	return (
		<Box sx={{ display: 'flex' }}>
			<CircularProgress />
		</Box>
	);
}